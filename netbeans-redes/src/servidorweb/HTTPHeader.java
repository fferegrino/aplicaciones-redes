package servidorweb;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 *
 * @author (at)fferegrino
 */
public class HTTPHeader {

    private final static String CONTENT_LENGTH_FLAG = "th:";
    public final static String METHOD_GET = "GET";
    public final static String METHOD_POST = "POST";
    BufferedReader br;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public HashMap<String, String> getParametros() {
        return parametros;
    }

    public void setParametros(HashMap<String, String> parametros) {
        this.parametros = parametros;
    }
    private String method;
    private String file;
    private HashMap<String, String> parametros;

    public HTTPHeader(InputStream is) {
        this.br = new BufferedReader(new InputStreamReader(is));
        this.parametros = new HashMap<String, String>();
    }

    void parse() throws IOException {
        String s = br.readLine();
        String[] fields = s.split(" ");
        method = fields[0];
        file = fields[1];
        int qmark = file.indexOf("?");
        String fullParams = null;
        if (qmark >= 0) {
            fullParams = file.substring(qmark + 1);
            file = file.substring(0, qmark);
        }
        if (METHOD_GET.equals(method)) {
            // TODO: Algo 
        } else if (METHOD_POST.equals(method)) {
            String xD = br.readLine();
            int mxRead = 0;
            while (xD.length() > 0) {
                xD = br.readLine();
                if (xD.indexOf(CONTENT_LENGTH_FLAG) > 0) {
                    mxRead = Integer.parseInt(
                            xD.substring(xD.indexOf(CONTENT_LENGTH_FLAG)
                            + CONTENT_LENGTH_FLAG.length() + 1));
                }
            }
            char[] data = new char[mxRead];
            br.read(data, 0, mxRead);
            fullParams = new String(data);
        }
        if(fullParams != null) {
            fillParams(fullParams.split("&"));
        }
    }

    private void fillParams(String[] paramString) {
        for (String s : paramString) {
            String[] pp = s.split("=");
            if (pp.length != 2) {
                parametros.put(pp[0], "");
            } else {
                parametros.put(pp[0], pp[1]);
            }
        }
    }
}
