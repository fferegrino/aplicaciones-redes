using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace SocketUtils.Readers
{
    public class FileReader : SimpleReader
    {
        private FileReader()
            : base(null)
        {

        }

        public FileReader(Socket s)
            : base(s)
        {

        }

        public bool ReadFile(string path)
        {
            bool ok = true;
            byte[] t = new byte[ReadInt32()];
            base.s.Receive(t);
            File.WriteAllBytes(path, t);
            return ok;
        }
    }
}
