using Newtonsoft.Json;
using OmuBumu.Common;
using OmuBumu.Common.Types;
using OmuBumu.Helper;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Popups;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

namespace OmuBumu
{

    public sealed partial class UyeProfili : Page
    {
        public UyeProfili()
        {
            this.InitializeComponent();
        }

        async Task ProfilDoldur(string UyeID = "")
        {
            try
            {
                ResultContext listSorular = null;
                if (!string.IsNullOrEmpty(UyeID))
                {
                    var uyeSonuc = await App.APIService.UyeProfil(UyeID);
                    if (uyeSonuc != null && uyeSonuc.Sonuc)
                    {
                        var uye = JsonConvert.DeserializeObject<User>(JsonConvert.SerializeObject(uyeSonuc.Data));
                        if (uye == null)
                        {
                            await Mesaj.MesajGoster("Hata Oluştu");
                            if (Navigator.CurrentFrame.CanGoBack)
                                Navigator.CurrentFrame.GoBack();
                        }
                        listSorular = await App.APIService.Sorular("", "", UyeID);
                        adsoyad.Text = uye.AdiSoyadi;
                        nickname.Text = "@" + uye.KullaniciAdi;
                        imgProfile.Fill = new ImageBrush()
                        {
                            ImageSource = await FileHelper.ByteToImage(await FileHelper.GetDataFromUri(new Uri(uye.ProfileImage, UriKind.Absolute)))
                        };
                    }
                    else if (uyeSonuc != null)
                    {
                        await Mesaj.MesajGoster(uyeSonuc.Mesaj);
                        if (Navigator.CurrentFrame.CanGoBack)
                            Navigator.CurrentFrame.GoBack();
                    }
                }
                else if (GirisPage.Uye.UyeID != null)
                {
                    listSorular = await App.APIService.Sorular("", "", GirisPage.Uye.UyeID);
                    adsoyad.Text = GirisPage.Uye.AdiSoyadi;
                    nickname.Text = "@" + GirisPage.Uye.KullaniciAdi;
                    imgProfile.Fill = new ImageBrush()
                    {
                        ImageSource = await FileHelper.ByteToImage(await FileHelper.GetDataFromUri(new Uri(GirisPage.Uye.ProfileImage, UriKind.Absolute)))
                    };
                }
                else
                {
                    await Mesaj.MesajGoster("Hata var");
                }
                if (listSorular != null && listSorular.Sonuc)
                    gridView.ItemsSource = JsonConvert.DeserializeObject<List<Soru>>(JsonConvert.SerializeObject(listSorular.Data));

            }
            catch (Exception ex)
            {
                await App.APIService.Log("Üye Profili Hatası. Detaylar: " + ex.Message);
            }
        }

        protected override async void OnNavigatedTo(NavigationEventArgs e)
        {
            await ProfilDoldur((e.Parameter != null) ? e.Parameter.ToString() : "");
        }

        private void gridView_ItemClick(object sender, ItemClickEventArgs e)
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

