using SocketUtils.Readers;
using System;
using System.Collections.Generic;
using System.IO;
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
            string pathName = "C:/ftp/cs/";


            try
            {
                ss.Bind(localEndPoint);
                ss.Listen(10);
                while (true)
                {
                    Console.WriteLine("Servidor escuchando por conexiones");
                    Socket cliente = ss.Accept();
                    Console.WriteLine("Conexion aceptada " + cliente.LocalEndPoint.ToString());
                    FileReader sr = new FileReader(cliente);


                    int f = sr.ReadInt32();

                    for (int i = 0; i < f; i++)
                    {
                        string fileName = sr.ReadString();
                        Console.WriteLine("\t"+fileName);
                        sr.ReadFile(Path.Combine(pathName, fileName));
                    }


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
