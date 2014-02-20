using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace Carrito.Wpf.Cliente.Commands
{
    public class ActionCommand : ICommand
    {
        Action a;
        public bool CanExecute(object parameter)
        {
            return true;
        }

        public ActionCommand(Action a)
        {
            this.a = a;
        }

        public void Execute(object parameter)
        {
            a();
        }

        public event EventHandler CanExecuteChanged;
    }
}
