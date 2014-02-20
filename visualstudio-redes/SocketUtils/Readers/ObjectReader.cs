using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading.Tasks;

namespace SocketUtils.Readers
{
    public class ObjectReader : SimpleReader
    {
        private ObjectReader() : base(null) { }

        public ObjectReader(Socket s) : base(s) { }

        public TObject ReadObject<TObject>() where TObject : class
        {
            TObject t;
            int size = ReadInt32();
            byte[] b = new byte[size];
            int resultado = ReadBytes(b,size);
            using (MemoryStream ms = new MemoryStream(b))
            {
                BinaryFormatter bf = new BinaryFormatter();
                try
                {
                    ms.Position = 0;
                    t = (TObject)bf.Deserialize(ms);
                }
                catch
                {
                    t = null;
                }
                ms.Close();
                ms.Dispose();
            }
            return t;
        }
    }
}
