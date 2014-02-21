using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Carrito.Shared.Entidades
{
    [Serializable]
    public class Orden
    {
        public int ProductId { get; set; }
        public int Cantidad { get; set; }
    }
}
