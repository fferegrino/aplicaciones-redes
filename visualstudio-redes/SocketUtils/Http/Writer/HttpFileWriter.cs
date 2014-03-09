using System;
using System.IO;
using System.Linq;
using System.Net.Sockets;

namespace SocketUtils.Http.Writer
{
    public class HttpFileWriter
    {

        private readonly NetworkStream ns;

        public HttpFileWriter(NetworkStream ns)
        {
            this.ns = ns;
        }

        public HttpFileWriter(TcpClient cliente)
            : this(cliente.GetStream())
        {

        }

        public void WriteFile(string path)
        {
            byte[] b = File.ReadAllBytes(path);
            ns.Write(b, 0, b.Length);
        }
    }
}
