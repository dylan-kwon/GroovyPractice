import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import java.nio.charset.Charset

import com.intland.codebeamer.persistence.dto.TrackerItemDto
import com.intland.codebeamer.event.util.VetoException

final def IS_TEST_MODE = false

try {
    if (!beforeEvent) {
        return
    }

    if (IS_TEST_MODE) {
        localTest()

    } else {
        requestCodebeamer()
    }

} catch (VetoException e) {
    e.printStackTrace()
    return
}

/**
 * Local Test
 */
static def localTest() {
    openConnection(
            'http://localhost:8000/api/kakao/channels/',

            'POST',

            [
                    'Content-Type': 'application/json',
                    'is-test'     : 'true',
                    'method'      : 'POST',
                    'user-id'     : '1',
                    'user-type'   : '1'
            ],

            [
                    'type': '0',
                    'page': '10'
            ],

            { int responseCode, Map<String, String> responseData ->
//                println('local test api onSuccess!!,' + responseData['result_msg'])
                logger.info('local test api onSuccess!!,' + responseData['result_msg'])

            },

            { int responseCode ->
//                println("local test api onFail!!")
                logger.info("local test api onFail!!")
            }
    )
}

/**
 * Codebeamer TestRun
 */
def requestCodebeamer() {
    openConnection(
            'http://115.178.77.131:8070/cb/rest/testRun',

            'POST',

            [
                    'Content-Type': 'application/x-www-form-urlencoded',
                    Authorization : 'Basic ' + 'admin:finger'.bytes.encodeBase64().toString()
            ],

            [
                    'testSetId': String.valueOf(subject.id)
//                    'testSetId': '68725'
            ],

            { int responseCode, Map<String, String> responseData ->
//                println("requestCodebeamer onSuccess!!")
                logger.info("requestCodebeamer onSuccess!!")
                requestJenkins(responseData)

            },

            { int responseCode ->
//                println("requestCodebeamer onFail!!")
                logger.info("requestCodebeamer onFail!!")
            }
    )
}

/**
 * Jenkins
 */
def requestJenkins(Map<String, String> testRunResponse) {
    openConnection(
            'http://115.178.77.131:8555/job/New%20Item/build',

            'POST',

            [
                    "Content-Type": "application/json",
                    Authorization : 'Basic ' + 'admin:2c862ab2505a344a5b9edb80b98ea7de'.bytes.encodeBase64().toString()
            ],

            [
                    'parameter': JsonOutput.toJson(testRunResponse)
            ],

            { int responseCode, Map<String, String> responseData ->
//                println("requestJenkins onSuccess!!")
                logger.info("requestJenkins onSuccess!!")

            },

            { int responseCode ->
//                println("requestJenkins onFail!!")
                logger.info("requestJenkins onFail!!")
            }
    )
}

/**
 * open connection
 */
def openConnection(String apiUrl, String method, Map<String, String> header, Map<String, String> body,
                   def onSuccess, def onFail) {

    // connection
    URL url = new URL(apiUrl)
    HttpURLConnection connection = url.openConnection() as HttpURLConnection

    // option
    connection.setRequestMethod(method)
    connection.setDoInput(true)
    connection.setDoOutput(true)
    connection.setUseCaches(true)
    connection.setDefaultUseCaches(true)
    connection.setReadTimeout(10_000)
    connection.setConnectTimeout(10_000)

    // header
    setHeader(connection, header)

    // body
    setBody(connection, body)

    // response
    def responseCode = connection.getResponseCode()
//    println("responseCode: $responseCode, $connection.responseMessage")
    logger.info("responseCode: $responseCode, $connection.responseMessage")

    // request success
    switch (responseCode) {
        case [HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_CREATED, HttpURLConnection.HTTP_ACCEPTED]:

            def response = makeResponseJson(connection)
            onSuccess(responseCode, response.properties['content'])

            break

        default:
            onFail(responseCode)
            break
    }
}

/**
 * set header payload.
 */
def setHeader(HttpURLConnection connection, Map<String, String> header) {
    for (e in header) {
        connection.setRequestProperty(e.key, e.value)
    }
//    println("header -> " + new JsonBuilder(header).toPrettyString())
    logger.info("header -> " + new JsonBuilder(header).toPrettyString())
}

/**
 * set body payload.
 */
def setBody(HttpURLConnection connection, Map<String, String> body) {

    def bodyPayload = ""
    def contentType = connection.getRequestProperty('Content-Type') ?: ""

    switch (contentType) {
        case 'application/json':
            bodyPayload = new JsonBuilder(body).toString()
            break

        case 'application/x-www-form-urlencoded':
            for (e in body) {
                bodyPayload += "$e.key=$e.value&"
            }
            if (bodyPayload.endsWith('&')) {
                bodyPayload = bodyPayload.substring(0, bodyPayload.length() - 1)
            }
            break

        default:
            return
    }

    def os = connection.getOutputStream()
    os.write(bodyPayload.toString().getBytes())

    os.flush()
    os.close()

//    println("body -> " + new JsonBuilder(body).toPrettyString())
    logger.info("body -> " + new JsonBuilder(body).toPrettyString())
}

/**
 * response payload to Json.
 */
def JsonBuilder makeResponseJson(HttpURLConnection connection) {
    def bufferSize = 1024
    def charset = Charset.forName("UTF-8")

    def is = connection.getInputStream()
    def byteBuffer = new byte[bufferSize]
    def len = is.read(byteBuffer, 0, byteBuffer.size())

    def baos = new ByteArrayOutputStream()

    while (len != -1) {
        baos.write(byteBuffer, 0, len)
        len = is.read(byteBuffer, 0, byteBuffer.size())
    }

    def byteData = baos.toByteArray()
    def strJsonResponse = new String(byteData, charset)

    try {
        def jsonResponse = new JsonBuilder(new JsonSlurper()
                .parseText(strJsonResponse) as Map<String, String>)

//        println("responseJson -> ${jsonResponse.toPrettyString()}")
        logger.info("responseJson = ${jsonResponse.toPrettyString()}")

        return jsonResponse


    } catch (IllegalArgumentException ignored) {

        // empty jsonBuilder.
        return new JsonBuilder(new HashMap<String, String>())
    }

}