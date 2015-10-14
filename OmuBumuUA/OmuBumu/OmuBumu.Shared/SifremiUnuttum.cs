using System;
using System.Collections.Generic;
using System.Text;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Input;

namespace OmuBumu
{
    public sealed partial class SifremiUnuttum : Page
    {
        public SifremiUnuttum()
        {
            this.InitializeComponent();
        }

        private async void Gonder_Click(object sender, RoutedEventArgs e)
        {
            try {
                var sonuc = await App.APIService.SifremiUnuttum(txtEmail.Text);
                if (sonuc != null)
                    await Mesaj.MesajGoster(sonuc.Mesaj);
                if (Navigator.CurrentFrame.CanGoBack)
                    Navigator.CurrentFrame.GoBack();
            }
            catch (Exception ex)
            {
                await App.APIService.Log("Şifremi Unuttum Hatası. Detaylar: " + ex.Message);
            }
        }

        private void Geri_Tapped(object sender, TappedRoutedEventArgs e)
        {
            Navigator.CurrentFrame.GoBack();
        }
    }
}
