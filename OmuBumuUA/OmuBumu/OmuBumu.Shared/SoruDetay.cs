using Newtonsoft.Json;
using OmuBumu.Common;
using OmuBumu.Common.Types;
using OmuBumu.Helper;
using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Threading.Tasks;
using Windows.ApplicationModel.Activation;
using Windows.ApplicationModel.Core;
using Windows.Graphics.Display;
using Windows.Storage;
using Windows.Storage.Pickers;
using Windows.UI.Popups;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

namespace OmuBumu
{
    public sealed partial class SoruDetay : Page
    {
        int type = 1;
        Soru soru;
        StorageFile resimfile1;

        CoreApplicationView view;

        public SoruDetay()
        {
            this.InitializeComponent();
            this.Loaded += SoruDetay_Loaded;
        }

        async Task YorumList()
        {
            try
            {
                bool IsStarted = progressBar.IsActive;
                if (!IsStarted)
                    progressBar.IsActive = true;
                var yorumlist = await App.APIService.Yorumlar(soru.SoruID);
                listYorumlar.ItemsSource = JsonConvert.DeserializeObject<List<Yorumlar>>(JsonConvert.SerializeObject(yorumlist.Data));
                if (!IsStarted)
                    progressBar.IsActive = false;
            }
            catch (Exception ex)
            {
                await App.APIService.Log("Yorum Listeleme Hatası. Detaylar: " + ex.Message);
            }
        }


        async Task OyBilgiGunclle()
        {
            try
            {
                progressBar.IsActive = true;
                var list = await App.APIService.Oylar(soru.SoruID);
                var oyIstatisktik = JsonConvert.DeserializeObject<Oylar>(JsonConvert.SerializeObject(list.Data));
                imgOy1.Text = oyIstatisktik.Resim1Oy;
                imgOy2.Text = oyIstatisktik.Resim2Oy;
                progressBar.IsActive = false;
            }
            catch (Exception ex)
            {
                await App.APIService.Log("Oy Güncelle Hatası. Detaylar: " + ex.Message);
            }
        }
        private async void SoruDetay_Loaded(object sender, Windows.UI.Xaml.RoutedEventArgs e)
        {
            try
            {
                progressBar.IsActive = true;
                txtTarih.Text = soru.SaveDate;
                txtKategoriAdi.Text = soru.KategoriAdi;
                txtBaslik.Text = soru.Baslik;
                var uyeResult = await App.APIService.UyeProfil(soru.UyeID);
                var uye = JsonConvert.DeserializeObject<User>(JsonConvert.SerializeObject(uyeResult.Data));
                txtKullaniciAdi.Text = "@" + uye.KullaniciAdi;
                var img1Data = await FileHelper.GetDataFromUri(new Uri(soru.Resim1, UriKind.Absolute));
                var img2Data = await FileHelper.GetDataFromUri(new Uri(soru.Resim2, UriKind.Absolute));
                kullaniciprofilresmi.Fill = new ImageBrush()
                {
                    ImageSource = await FileHelper.ByteToImage(await FileHelper.GetDataFromUri(new Uri(uye.ProfileImage, UriKind.Absolute)))
                };
                imgResim1.Source = await FileHelper.ByteToImage(img1Data);
                imgResim2.Source = await FileHelper.ByteToImage(img2Data);

                await YorumList();
                await OyBilgiGunclle();
                progressBar.IsActive = false;
            }
            catch (Exception ex)
            {
                await App.APIService.Log("Soru Detay Hatası. Detaylar: " + ex.Message);
                Navigator.CurrentFrame.GoBack();
            }
        }
        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            Common.Types.SoruDetay detay = (Common.Types.SoruDetay)e.Parameter;
            if (detay != null)
            {
                soru = detay.Soru;
            }
            else
            {
                Frame.GoBack();
            }
        }

        async Task Gonder()
        {
            try {
                progressBar.IsActive = true;
                if (!string.IsNullOrEmpty(GirisPage.Uye.UyeID) && !string.IsNullOrEmpty(soru.SoruID) && txtYorum.Text != null)
                {
                    ResultContext result;
                    if (resimfile1 == null)
                    {
                        result = await App.APIService.YorumEkle(soru.SoruID, txtYorum.Text, null, null);
                    }
                    else
                    {
                        var YorumResim = await FileHelper.ReadFile(resimfile1);
                        result = await App.APIService.YorumEkle(soru.SoruID, txtYorum.Text, YorumResim, resimfile1.Name);
                    }
                    await Mesaj.MesajGoster(result.Mesaj);
                    if (result.Sonuc)
                        await YorumList();
                }
                else
                {
                    await Mesaj.MesajGoster("Dosya seçilirken hata oluştu");
                }
                progressBar.IsActive = false;
            }
            catch (Exception ex)
            {
                await App.APIService.Log("Gönderme Hatası. Detaylar: " + ex.Message);
            }
        }
        async Task Oyla(bool Resim)
        {
            try
            {
                progressBar.IsActive = true;
                var oyla = await App.APIService.Oyla(soru.SoruID, Resim);
                if (oyla != null)
                    await OyBilgiGunclle();
                progressBar.IsActive = false;
            }
            catch (Exception ex)
            {
                await App.APIService.Log("Oylama Hatası. Detaylar: " + ex.Message);
            }
        }
        private async void listYorumlar_ItemClick(object sender, ItemClickEventArgs e)
        {
            try {
                var yorum = (Yorumlar)e.ClickedItem;
                Frame.Navigate(typeof(UyeProfili), yorum.UyeID);
            }
            catch (Exception ex)
            {
                await App.APIService.Log("Yorum Hatası. Detaylar: " + ex.Message);
            }
        }

        private async void Resim1_Tapped(object sender, TappedRoutedEventArgs e)
        {
            try {
                await Oyla(true);
            }
            catch (Exception ex)
            {
                await App.APIService.Log("Resim 1 Oy verme Hatası. Detaylar: " + ex.Message);
            }
        }

        private async void Resim2_Tapped(object sender, TappedRoutedEventArgs e)
        {
            try
            {
                await Oyla(false);
            }
            catch (Exception ex)
            {
                await App.APIService.Log("Resim 2 Oy verme Hatası. Detaylar: " + ex.Message);
            }
        }

        public async void YorumYap_Tapped(object sender, TappedRoutedEventArgs e)
        {
            await Gonder();
        }

        private void Geri_Tapped(object sender, TappedRoutedEventArgs e)
        {
            Navigator.CurrentFrame.GoBack();
        }

        private async void ResimSec_Tapped(object sender, TappedRoutedEventArgs e)
        {
            try
            {
                view = CoreApplication.GetCurrentView();
                FileOpenPicker openPicker = new FileOpenPicker();
                openPicker.ViewMode = PickerViewMode.Thumbnail;
                openPicker.SuggestedStartLocation = PickerLocationId.PicturesLibrary;
                openPicker.FileTypeFilter.Add(".jpg");
                openPicker.FileTypeFilter.Add(".jpeg");
                openPicker.FileTypeFilter.Add(".png");
#if WINDOWS_PHONE_APP
            view.Activated += SoruDetay_Activated;
            openPicker.PickSingleFileAndContinue();
#else
                resimfile1 = await openPicker.PickSingleFileAsync();
#endif
            }

            catch (Exception ex)
            {
                await App.APIService.Log("Resim Sec Hatası. Detaylar: " + ex.Message);
            }
        }

        private void SoruDetay_Activated(CoreApplicationView sender, Windows.ApplicationModel.Activation.IActivatedEventArgs args)
        {
#if WINDOWS_PHONE_APP
            FileOpenPickerContinuationEventArgs param = args as FileOpenPickerContinuationEventArgs;

            if (param != null)
            {
                if (param.Files.Count == 0) return;

                view.Activated -= SoruDetay_Activated;
                if (type == 1)
                    resimfile1 = param.Files[0];
            }
#endif
        }

        private void Grid_Loaded(object sender, RoutedEventArgs e)
        {
            var img = (Grid)sender;
            var info = DisplayInformation.GetForCurrentView();
            if ((info.RawDpiX > 200 & info.RawDpiX < 250) & (info.RawDpiY > 200 & info.RawDpiY < 250)) //4 inch
            {
                img.Width = 400;
            }
            else if ((info.RawDpiX > 350 & info.RawDpiX < 400) & (info.RawDpiY > 350 & info.RawDpiY < 400))
            {
                img.Width = 490;
            }
            else if ((info.RawDpiX > 390 & info.RawDpiX < 450) & (info.RawDpiY > 390 & info.RawDpiY < 450))
            {
                img.Width = 450;
            }
            else if ((info.RawDpiX > 300 & info.RawDpiX < 350) & (info.RawDpiY > 300 & info.RawDpiY < 350))
            {
                img.Width = 400;
            }
        }
    }
}
