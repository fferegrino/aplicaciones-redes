package servidorweb;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author (at)fferegrino
 */
public class HTTPHeader {

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

    public HTTPHeader(BufferedReader br) {
        this.br = br;
        this.parametros = new HashMap<String, String>();
    }

    void parse() throws IOException {
        String s = br.readLine();
        String[] fields = s.split(" ");
        method = fields[0];
        file = fields[1];
        if (METHOD_GET.equals(method)) {
            int qmark = file.indexOf("?");
            if (qmark >= 0) {
                String fullParams = file.substring(qmark + 1);
                file = file.substring(0, qmark);
                fillParams(fullParams.split("&"));
            }
        } else if (METHOD_POST.equals(method)) {
            String xD = br.readLine();
            while ( 
                    xD.length() > 0
//                    !"\r\n".equals(xD) || 
//                    !"\n\r".equals(xD)
                    ) {
                System.out.println("Leí " + xD.length());
                xD = br.readLine();
            }
            String params = br.readLine();
            System.out.println(params);
        }
        //br.close();
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
