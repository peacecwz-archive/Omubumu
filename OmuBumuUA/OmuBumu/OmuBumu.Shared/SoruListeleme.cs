using Newtonsoft.Json;
using OmuBumu.Common;
using OmuBumu.Common.Types;
using System;
using System.Collections.Generic;
using System.Text;
using Windows.Graphics.Display;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Navigation;

namespace OmuBumu
{
    public sealed partial class SoruListeleme : Page
    {
        public SoruListeleme()
        {
            this.InitializeComponent();
            this.Loaded += SoruListeleme_Loaded;
        }

        private async void SoruListeleme_Loaded(object sender, Windows.UI.Xaml.RoutedEventArgs e)
        {
            try
            {

                progressBar.IsActive = true;
                var list = await App.APIService.Sorular();
                gridView.ItemsSource = JsonConvert.DeserializeObject<List<Soru>>(JsonConvert.SerializeObject(list.Data));
                progressBar.IsActive = false;
            }
            catch (Exception ex)
            {
                await App.APIService.Log("Soru Listeleme Hatası. Detaylar: " + ex.Message);
            }
        }

        private async void gridView_ItemClick(object sender, ItemClickEventArgs e)
        {
            Frame.Navigate(typeof(SoruDetay), new Common.Types.SoruDetay()
            {
                Soru = (Soru)e.ClickedItem
            });
        }

        private void Geri_Tapped(object sender, TappedRoutedEventArgs e)
        {
            Navigator.CurrentFrame.GoBack();
        }
    }
}
