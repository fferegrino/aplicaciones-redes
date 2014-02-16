using SocketUtils.Readers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace Ftp.Servidor
{
    class Program
    {
        static void Main(string[] args)
        {
            IPEndPoint localEndPoint = new IPEndPoint(IPAddress.Loopback, 4040);
            Socket ss = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            

            try
            {
                ss.Bind(localEndPoint);
                ss.Listen(10);
                while (true)
                {
                    Console.WriteLine("Servidor escuchando por conexiones");
                    Socket cliente = ss.Accept();
                    Console.WriteLine("Conexion aceptada " + cliente.LocalEndPoint.ToString());
                    SimpleReader sr = new SimpleReader(cliente);


                    int f = sr.ReadInt32();
                    Console.WriteLine("Files: " + f);


                    Console.WriteLine("Conexion terminada " + cliente.LocalEndPoint.ToString());
                    cliente.Shutdown(SocketShutdown.Both);
                    cliente.Close();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }

        }
    }
}
