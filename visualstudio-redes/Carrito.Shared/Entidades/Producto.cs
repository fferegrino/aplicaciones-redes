using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Carrito.Shared.Entidades
{
    [Serializable]
    public class Producto
    {
        public string Nombre { get; set; }
        public string Descripcion { get; set; }
        public int CantidadDisponible { get; set; }
        public decimal Precio { get; set; }

    }
}
