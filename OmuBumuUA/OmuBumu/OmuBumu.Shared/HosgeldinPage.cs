using Newtonsoft.Json;
using OmuBumu.Common.Types;
using OmuBumu.Helper;
using System;
using System.Collections.Generic;
using System.Text;
using Windows.UI.Popups;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;
namespace OmuBumu
{
    public sealed partial class HosgeldinPage : Page
    {
        public HosgeldinPage()
        {
            this.InitializeComponent();
            Loaded += HosgeldinPage_Loaded;
        }

        async void HosgeldinPage_Loaded(object sender, RoutedEventArgs e)
        {
            txtKullaniciAdi.Text = GirisPage.Uye.KullaniciAdi;
            imgProfil.Fill = new ImageBrush()
            {
                ImageSource = await FileHelper.ByteToImage(await FileHelper.GetDataFromUri(new Uri(GirisPage.Uye.ProfileImage, UriKind.Absolute)))
            };
        }

        private void SoruNaviButton_Click(object sender, RoutedEventArgs e)
        {
            Navigator.CurrentFrame.Navigate(typeof(SoruEkle));
        }

        private void SoruListelemeNaviButton_Click(object sender, RoutedEventArgs e)
        {
            Navigator.CurrentFrame.Navigate(typeof(SoruListeleme));
        }

        private void Profile_Tapped(object sender, TappedRoutedEventArgs e)
        {
            Navigator.CurrentFrame.Navigate(typeof(UyeProfili));
        }

        private void ProfileGuncelle_Tapped(object sender, TappedRoutedEventArgs e)
        {
            Navigator.CurrentFrame.Navigate(typeof(ProfilGuncelle));
        }

        private async void Cikis_Tapped(object sender, TappedRoutedEventArgs e)
        {
            var msj = new MessageDialog("Çıkış yapmak istiyor musunuz?", "O mu Bu mu");
            msj.Commands.Add(new UICommand("Uygulamadan Çık", (sndr) =>
            {
                App.Current.Exit();
            }));
            msj.Commands.Add(new UICommand("Hesabımdan Çık", async (sndr) =>
             {
                 await GirisPage.CikisYap();
             }));
            msj.CancelCommandIndex = 1;
            msj.DefaultCommandIndex = 1;
            await msj.ShowAsync();
        }
    }
}
