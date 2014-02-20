using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using Carrito.Shared.Entidades;
using System.Net;
using SocketUtils.Readers;
using SocketUtils.Writers;
using Carrito.Shared.Enums;

namespace Carrito.Wpf.Cliente.Models
{
    public class CarritoModel
    {

        public ObservableCollection<Producto> RecuperaProductos()
        {
            var resultado = new ObservableCollection<Producto>();
            IPEndPoint remotePoint = new IPEndPoint(IPAddress.Loopback, 4040);
            Socket s = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            s.Connect(remotePoint);
            ObjectReader r = new ObjectReader(s);
            ObjectWriter w = new ObjectWriter(s);

            w.WriteInt32((int)Transacciones.SolicitarCarrito);
            int cont = r.ReadInt32();
            for (int i = 0; i < cont; i++)
            {
                Producto p = r.ReadObject<Producto>();
                if (p != null)
                    resultado.Add(p);
            }

            s.Shutdown(SocketShutdown.Both);
            s.Close();
            return resultado;
        }

    }
}
