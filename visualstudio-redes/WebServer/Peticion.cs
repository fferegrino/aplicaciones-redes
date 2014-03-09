using SocketUtils.Http;
using SocketUtils.Http.Readers;
using SocketUtils.Writers;
using System;
using System.Collections.Generic;
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
        private SimpleWriter sw;

        public Peticion(TcpClient cliente)
        {
            this.cliente = cliente;
            r = new HttpHeaderReader(cliente);
        }

        public void Run()
        {
            Console.WriteLine("Cliente conectado desde: " + cliente.Client.RemoteEndPoint);

            HttpHeader hh = r.Read();
            Console.WriteLine(hh.FileRequested);
            cliente.Close();
        }

    }
}
