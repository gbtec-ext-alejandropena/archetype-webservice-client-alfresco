#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${rootArtifactId}.dao.impl.alfresco.jersey;


import com.sun.jersey.api.client.AbstractClientRequestAdapter;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientRequestAdapter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.core.util.ReaderWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Filter que permite loguear el intercambio de mensajes REST
 *
 *  
 */
public class JerseyLoggingFilter extends ClientFilter {

    private static final String NOTIFICATION_PREFIX = "* ";
    private static final String REQUEST_PREFIX = "> ";
    private static final String RESPONSE_PREFIX = "< ";

    private final class Adapter extends AbstractClientRequestAdapter {

        private final StringBuilder b;

        Adapter(ClientRequestAdapter cra, StringBuilder b) {
            super(cra);
            this.b = b;
        }

        @Override
        public OutputStream adapt(ClientRequest request, OutputStream out) throws IOException {
            return new LoggingOutputStream(getAdapter().adapt(request, out), b);
        }
    }

    private final class LoggingOutputStream extends OutputStream {

        private final OutputStream out;
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        private final StringBuilder b;

        LoggingOutputStream(OutputStream out, StringBuilder b) {
            this.out = out;
            this.b = b;
        }

        @Override
        public void write(byte[] b) throws IOException {
            baos.write(b);
            out.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            baos.write(b, off, len);
            out.write(b, off, len);
        }

        @Override
        public void write(int b) throws IOException {
            baos.write(b);
            out.write(b);
        }

        @Override
        public void close() throws IOException {
            printEntity(b, baos.toByteArray());
            log(b);
            out.close();
        }
    }
    private long idJersey = 0;

    private void log(StringBuilder b) {
        //LOG
    }

    @Override
    public ClientResponse handle(ClientRequest request) {
        long id = ++this.idJersey;
        logRequest(id, request);

        ClientResponse response = getNext().handle(request);

        logResponse(id, response);

        return response;
    }

    private void logRequest(long id, ClientRequest request) {
        StringBuilder b = new StringBuilder();

        printRequestLine(b, id, request);
        printRequestHeaders(b, id, request.getHeaders());

        if (request.getEntity() != null) {
            request.setAdapter(new Adapter(request.getAdapter(), b));
        } else {
            log(b);
        }
    }

    private StringBuilder prefixId(StringBuilder b, long id) {
        b.append(Long.toString(id)).append(" ");
        return b;
    }

    private void printRequestLine(StringBuilder b, long id, ClientRequest request) {
        prefixId(b, id).append(NOTIFICATION_PREFIX).append("Client out-bound request").append("${symbol_escape}n");
        prefixId(b, id).append(REQUEST_PREFIX).append(request.getMethod()).append(" ").
                append(request.getURI().toASCIIString()).append("${symbol_escape}n");
    }

    private void printRequestHeaders(StringBuilder b, long id, MultivaluedMap<String, Object> headers) {
        for (Map.Entry<String, List<Object>> e : headers.entrySet()) {
            List<Object> val = e.getValue();
            String header = e.getKey();

            if (val.size() == 1) {
                prefixId(b, id).append(REQUEST_PREFIX).append(header).append(": ").append(ClientRequest.getHeaderValue(val.get(0))).append("${symbol_escape}n");
            } else {
                StringBuilder sb = new StringBuilder();
                boolean add = false;
                for (Object o : val) {
                    if (add) {
                        sb.append(',');
                    }
                    add = true;
                    sb.append(ClientRequest.getHeaderValue(o));
                }
                prefixId(b, id).append(REQUEST_PREFIX).append(header).append(": ").append(sb.toString()).append("${symbol_escape}n");
            }
        }
    }

    private void logResponse(long id, ClientResponse response) {
        StringBuilder b = new StringBuilder();

        printResponseLine(b, id, response);
        printResponseHeaders(b, id, response.getHeaders());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = response.getEntityInputStream();
        try {
            ReaderWriter.writeTo(in, out);

            byte[] requestEntity = out.toByteArray();
            printEntity(b, requestEntity);
            response.setEntityInputStream(new ByteArrayInputStream(requestEntity));
        } catch (IOException ex) {
            throw new ClientHandlerException(ex);
        }
        log(b);
    }

    private void printResponseLine(StringBuilder b, long id, ClientResponse response) {
        prefixId(b, id).append(NOTIFICATION_PREFIX).
                append("Client in-bound response").append("${symbol_escape}n");
        prefixId(b, id).append(RESPONSE_PREFIX).
                append(Integer.toString(response.getStatus())).
                append("${symbol_escape}n");
    }

    private void printResponseHeaders(StringBuilder b, long id, MultivaluedMap<String, String> headers) {
        for (Map.Entry<String, List<String>> e : headers.entrySet()) {
            String header = e.getKey();
            for (String value : e.getValue()) {
                prefixId(b, id).append(RESPONSE_PREFIX).append(header).append(": ").
                        append(value).append("${symbol_escape}n");
            }
        }
        prefixId(b, id).append(RESPONSE_PREFIX).append("${symbol_escape}n");
    }

    private void printEntity(StringBuilder b, byte[] entity) throws IOException {
        if (entity.length == 0) {
            return;
        }
        b.append(new String(entity, Charset.forName("UTF-8"))).append("${symbol_escape}n");
    }
}