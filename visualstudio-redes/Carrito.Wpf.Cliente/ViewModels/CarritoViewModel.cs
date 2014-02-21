using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Carrito.Shared.Entidades;
using Carrito.Wpf.Cliente.Commands;
using Carrito.Wpf.Cliente.Models;

namespace Carrito.Wpf.Cliente.ViewModels
{
    public class CarritoViewModel : BaseViewModel
    {
        private Producto elegido;

        public Producto Elegido
        {
            get { return elegido; }
            set { elegido = value; RaiseChange(); }
        }

        private int cantidadSolicitada;

        public int CantidadSolicitada
        {
            get { return cantidadSolicitada; }
            set { cantidadSolicitada = value; RaiseChange(); }
        }
        


        private ObservableCollection<Producto> productos;

        public ObservableCollection<Producto> Productos
        {
            get { return productos; }
            set { productos = value; RaiseChange(); }
        }

        private readonly CarritoModel model;

        private ActionCommand connectCommand;
        public ActionCommand ConnectCommand
        {
            get
            {
                if (connectCommand == null)
                {
                    connectCommand = new ActionCommand(() =>
                    {
                        Productos = model.RecuperaProductos();
                    });
                } return connectCommand;
            }
        }

        private ActionCommand comprarCommand;
        public ActionCommand ComprarCommand
        {
            get
            {
                if (comprarCommand == null)
                {
                    comprarCommand = new ActionCommand(() =>
                    {
                        if (CantidadSolicitada > 0)
                        {
                            model.RealizaCompra(Elegido.ProductId, CantidadSolicitada);
                            Productos = model.RecuperaProductos();
                        }
                    });
                } return comprarCommand;
            }
        }

        public CarritoViewModel()
        {
            model = new CarritoModel();
            Elegido = new Producto()
            {
                Nombre = "ljkasjdklajsd",
                Precio = (decimal)90.11,
                Descripcion = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum rutrum auctor gravida. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Ut ut volutpat enim. Mauris consequat magna ut sapien vulputate, vitae vulputate nunc rhoncus. Morbi fringilla dolor ac purus tincidunt laoreet id vitae quam. Suspendisse vulputate, sem vel fringilla lacinia, est lorem adipiscing quam, et venenatis justo nibh posuere quam. Suspendisse tristique, quam sed accumsan malesuada, urna felis semper neque, eget pretium lacus libero ac lectus."
            };
        }
    }
}
