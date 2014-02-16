using SocketUtils.Writers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Ftp.Cliente
{
    class Program
    {
        [STAThread]
        static void Main(string[] args)
        {
            byte[] buffer = new byte[1024];

            // Idea terrible usar SaveFileDialog en una aplicación de consola
            // Hecho solo con propósitos educativos:
            using (OpenFileDialog fileDialog = new OpenFileDialog())
            {
                fileDialog.Multiselect = true;
                if (fileDialog.ShowDialog() == DialogResult.OK)
                {
                    IPEndPoint remotePoint = new IPEndPoint(IPAddress.Loopback, 4040);
                    Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
                    s.Connect(remotePoint);
                    SimpleWriter sw = new SimpleWriter(s);

                    sw.WriteInt32(fileDialog.FileNames.Count());

                    s.Shutdown(SocketShutdown.Both);
                    s.Close();

                }
                Console.Read();
            }
        }
    }
}
