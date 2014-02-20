using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading.Tasks;

namespace SocketUtils.Writers
{
    public class ObjectWriter : SimpleWriter
    {
        private ObjectWriter() : base(null) { }

        public ObjectWriter(Socket s) : base(s) { }

        public void WriteObject<TObject>(TObject value) where TObject: class
        {
            BinaryFormatter bf = new BinaryFormatter();
            using (MemoryStream ms = new MemoryStream())
            {
                ms.Flush();
                bf.Serialize(ms, value);
                ms.Seek(0, SeekOrigin.Begin);
                byte[] b = ms.ToArray();
                ms.Flush();
                WriteInt32(b.Length);
                WriteBytes(b);
            }
        }
    }
}
