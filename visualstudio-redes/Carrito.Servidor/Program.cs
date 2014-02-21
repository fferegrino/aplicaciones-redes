using SocketUtils.Writers;
using SocketUtils.Readers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using System.Collections.ObjectModel;
using Carrito.Shared.Entidades;
using Carrito.Shared.Enums;

namespace Carrito.Servidor
{
    class Program
    {
        static void Main(string[] args)
        {
            IPEndPoint localEndPoint = new IPEndPoint(IPAddress.Loopback, 4040);
            Socket ss = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);


            var productos = new ObservableCollection<Producto>();
            for (int i = 0; i < 20; i++)
            {
                productos.Add(new Producto()
                {
                    ProductId = i,
                    Nombre = "Producto servidor" + i,
                    Precio = (decimal)i,
                    CantidadDisponible = i * 10,
                    Descripcion = "The enum keyword is used to declare an enumeration, a distinct type that consists of a set of named constants called the enumerator list. Usually it is best to"
                });
            }


            try
            {
                ss.Bind(localEndPoint);
                ss.Listen(10);
                while (true)
                {
                    Console.WriteLine("Servidor escuchando por conexiones");
                    Socket cliente = ss.Accept();
                    string descCliente = cliente.LocalEndPoint.ToString();
                    Console.WriteLine("Conexion aceptada " + descCliente);
                    ObjectWriter w = new ObjectWriter(cliente);
                    ObjectReader r = new ObjectReader(cliente);

                    Transacciones transaccion = (Transacciones)r.ReadInt32();
                    switch (transaccion)
                    {
                        case Transacciones.SolicitarCarrito:
                            Console.WriteLine("\tSolicitud de carrito por: " + descCliente);
                            w.WriteInt32(productos.Count);
                            for (int i = 0; i < productos.Count; i++)
                            {
                                w.WriteObject<Producto>(productos[i]);
                            }
                            break;
                        case Transacciones.RealizarCompra:
                            Console.WriteLine("\tOrden de compra de " + descCliente);
                            Orden o = r.ReadObject<Orden>();
                            productos[o.ProductId].CantidadDisponible -= o.Cantidad;
                            break;
                    }
                    Console.WriteLine("Conexion terminada " + descCliente);
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
