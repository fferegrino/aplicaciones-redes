using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace SocketUtils.Readers
{
    public class SimpleReader
    {
        private readonly int bufferSize;
        protected readonly Socket s;

        public int BufferSize { get { return this.bufferSize; } }

        byte[] buffer;

        private SimpleReader()
        {

        }

        public SimpleReader(Socket s)
            : this(s, 1024)
        {

        }

        public SimpleReader(Socket s, int bufferSize)
        {
            this.bufferSize = bufferSize;
            if (!s.IsBound || !s.Connected)
            {
                throw new Exception("Socket not connected");
            }
            this.s = s;
            buffer = new byte[bufferSize];
        }

        public int ReadBytes(byte[] b)
        {
            return s.Receive(b);
        }

        public string ReadString()
        {
            int size = ReadInt32();
            byte []b = new byte[size];
            s.Receive(b);
            char[] chars = new char[b.Length / sizeof(char)];
            System.Buffer.BlockCopy(b, 0, chars, 0, b.Length);
            return new string(chars);
        }

        public int ReadInt32()
        {
            s.Receive(buffer, 0, 4, SocketFlags.None);
            return BitConverter.ToInt32(buffer, 0);
        }
    }
}
