using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace WebServer
{
    class Program
    {
        public const int FinalPort = 8088;
        
        static void Main(string[] args)
        {
            IPEndPoint localEndPoint = new IPEndPoint(IPAddress.Loopback, FinalPort);
            TcpListener ss = new TcpListener(localEndPoint);

            try
            {
                ss.Start();
                while (true)
                {
                    TcpClient cliente = ss.AcceptTcpClient();
                    Peticion p = new Peticion(cliente);
                    ThreadStart ts = new ThreadStart(p.Run);
                    Thread hilo = new Thread(ts);
                    hilo.Start();

                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }
    }
}
