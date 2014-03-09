using SocketUtils.Http;
using SocketUtils.Http.Readers;
using SocketUtils.Http.Writer;
using SocketUtils.Writers;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Sockets;

namespace WebServer
{
    public class Peticion
    {
        public const string ServerRoute = "C:\\server";
        public const string Index = "\\index.html";
        private HttpHeaderReader r;
        private readonly TcpClient cliente;
        private HttpFileWriter w;

        public Peticion(TcpClient cliente)
        {
            this.cliente = cliente;
            r = new HttpHeaderReader(cliente);
            w = new HttpFileWriter(cliente);
        }

        public void Run()
        {
            Console.WriteLine("Cliente conectado desde: " + cliente.Client.RemoteEndPoint);

            HttpHeader hh = r.Read();
            Console.WriteLine(hh.FileRequested);
            hh.FileRequested = hh.FileRequested.Equals("/") ? Index : hh.FileRequested;
            if (File.Exists(ServerRoute + hh.FileRequested))
            {
                w.WriteFile(ServerRoute + hh.FileRequested);
            }
            cliente.Close();
        }

    }
}
