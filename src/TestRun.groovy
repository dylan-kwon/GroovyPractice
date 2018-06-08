import groovy.json.JsonBuilder

import java.nio.charset.Charset

final def method = 'POST'
final def apiUrl = 'http://115.178.77.208:8989/cb/rest/testRun'

final def doInput = true
final def doOutput = true
final def useCache = true

final def readTimeout = 10_000
final def connectTimeout = 10_000

// connection
URL url = new URL(apiUrl)
HttpURLConnection connection = url.openConnection() as HttpURLConnection

// option
connection.setRequestMethod(method)
connection.setDoInput(doInput)
connection.setDoOutput(doOutput)
connection.setUseCaches(useCache)
connection.setDefaultUseCaches(useCache)
connection.setReadTimeout(readTimeout)
connection.setConnectTimeout(connectTimeout)

// header
def header = [
        'Content-Type': 'application/x-www-form-urlencoded',
        Authorization : 'Basic ' + 'admin:finger'.bytes.encodeBase64().toString()
]
setHeader(connection, header)

// body
def body = [
//        'testSetId'  : '68725',
'testSetId'  : subject.id,
Authorization: 'Basic ' + 'admin:finger'.bytes.encodeBase64().toString()
]
setBody(connection, body)

// response
def responseCode = connection.getResponseCode()
println("responseCode: $responseCode, $connection.responseMessage")

// request success
if (responseCode == HttpURLConnection.HTTP_OK) {
    def response = makeResponseJson(connection)
    println("responseJson = ${response.toPrettyString()}")
}

/**
 * set header payload.
 */
static setHeader(HttpURLConnection connection, Map<String, String> header) {
    for (e in header) {
        connection.setRequestProperty(e.key, e.value)
    }
}

/**
 * set body payload.
 */
static setBody(HttpURLConnection connection, Map<String, String> body) {
    def bodyPayload = ""

    for (e in body) {
        bodyPayload += "$e.key=$e.value&"
    }

    if (bodyPayload.endsWith('&')) {
        bodyPayload.substring(0, bodyPayload.length() - 1)
    }

    def os = connection.getOutputStream()
    os.write(bodyPayload.toString().getBytes())

    os.flush()
    os.close()
}

/**
 * response payload to Json.
 */
static JsonBuilder makeResponseJson(HttpURLConnection connection) {
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
    def responseStr = new String(byteData, charset)

    return new JsonBuilder(responseStr)
}
