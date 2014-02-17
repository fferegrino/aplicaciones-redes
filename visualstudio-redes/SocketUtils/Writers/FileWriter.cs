using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace SocketUtils.Writers
{
    public class FileWriter:SimpleWriter
    {
        private FileWriter() : base(null) { }

        public FileWriter(Socket s) : base(s) { }

        public bool WriteFile(string path)
        {
            bool ok = true;
            byte[] b = File.ReadAllBytes(path);
            WriteInt32(b.Length);
            base.s.Send(b);
            return ok;
        }

    }
}
