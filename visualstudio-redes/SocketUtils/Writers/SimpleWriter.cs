using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace SocketUtils.Writers
{
    public class SimpleWriter
    {
        private readonly int bufferSize;
        private readonly Socket s;

        public int BufferSize { get { return this.bufferSize; } }

        byte[] buffer;

        private SimpleWriter()
        {

        }

        public SimpleWriter(Socket s)
            : this(s, 1024)
        {

        }

        public SimpleWriter(Socket s, int bufferSize)
        {
            this.bufferSize = bufferSize;
            if (!s.IsBound || !s.Connected)
            {
                throw new Exception("Socket not connected");
            }
            this.s = s;
            buffer = new byte[bufferSize];
        }

        public void WriteInt32(int value)
        {
            s.Send(BitConverter.GetBytes(value));
        }
    }
}
