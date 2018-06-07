import groovy.json.JsonBuilder

import java.nio.charset.Charset

final def method = 'POST'
final def apiUrl = 'http://115.178.77.208:8989/cb/rest/testRun'

final def doInput = true
final def doOutput = true
final def useCache = true

final def readTimeout = 10_000
final def connectTimeout = 10_000

//noinspection GroovyAssignabilityCheck
final def testSetId = this.args[0]
println("testSetId = $testSetId")

// connection
URL url = new URL(apiUrl)
HttpURLConnection request = url.openConnection() as HttpURLConnection

// option
request.setRequestMethod(method)
request.setDoInput(doInput)
request.setDoOutput(doOutput)
request.setUseCaches(useCache)
request.setDefaultUseCaches(useCache)
request.setReadTimeout(readTimeout)
request.setConnectTimeout(connectTimeout)

// header
def header = [
        'Content-Type': 'application/x-www-form-urlencoded',
        Authorization : 'Basic ' + 'admin:finger'.bytes.encodeBase64().toString()
]

for (e in header) {
    request.setRequestProperty(e.key, e.value)
}

// body
def body = "testSetId=$testSetId"
def os = request.getOutputStream()

os.write(body.getBytes())
os.flush()
os.close()

def responseCode = request.getResponseCode()
println("responseCode: $responseCode, $request.responseMessage")

if (responseCode == HttpURLConnection.HTTP_OK) {
    def response = makeResponseJson(request)
    println("responseJson = ${response.toPrettyString()}")
}

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