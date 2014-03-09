using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace SocketUtils.Http.Readers
{
    public class HttpHeaderReader
    {
        private const int BufferSize = 1024;
        private readonly byte[] buffer = new byte[BufferSize];

        private readonly NetworkStream ns;

        public HttpHeaderReader(NetworkStream ns)
        {
            this.ns = ns;
        }

        public HttpHeaderReader(TcpClient cliente)
            : this(cliente.GetStream())
        {

        }

        public HttpHeader Read()
        {
            int i = ns.Read(buffer, 0, BufferSize);
            string[] data = System.Text.Encoding.ASCII.GetString(buffer, 0, i).Split('\n');
            int init = data[0].IndexOf(' ') + 1;
            string file = data[0].Substring(init, data[0].LastIndexOf(' ') - init);
            return new HttpHeader() { FileRequested = file };
        }
    }
}
