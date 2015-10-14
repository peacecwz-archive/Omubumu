using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Popups;

namespace OmuBumu
{
    public class Mesaj
    {
        public async static Task MesajGoster(string mesaj)
        {
            try
            {
                var msj = new MessageDialog(mesaj, "O mu Bu mu");
                msj.Options = MessageDialogOptions.AcceptUserInputAfterDelay;
                await msj.ShowAsync();
            }
            catch { }
        }

        public async static Task Soru(string mesaj, UICommandInvokedHandler yesHand)
        {
            try
            {
                var msj = new MessageDialog(mesaj, "O mu Bu mu");
                msj.Commands.Add(new UICommand("Evet", yesHand));
                msj.Commands.Add(new UICommand("Hayır", (sndr) => { }));
                msj.CancelCommandIndex = 1;
                msj.DefaultCommandIndex = 1;
                await msj.ShowAsync();
            }
            catch { }
        }
    }
}
