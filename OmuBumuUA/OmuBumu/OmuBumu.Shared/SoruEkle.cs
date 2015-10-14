using Newtonsoft.Json;
using OmuBumu.Common;
using OmuBumu.Common.Types;
using System;
using System.Collections.Generic;
using System.Text;
using Windows.Storage;
using Windows.Storage.Pickers;
using Windows.UI.Popups;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Navigation;
using System.Linq;
using Windows.ApplicationModel.Core;
using Windows.ApplicationModel.Activation;
using Windows.UI.Xaml.Input;
using OmuBumu.Helper;
using Windows.UI.Xaml.Shapes;
using Windows.UI.Xaml.Media;
using System.Threading.Tasks;

namespace OmuBumu
{
    public sealed partial class SoruEkle : Page
    {
        public SoruEkle()
        {
            this.InitializeComponent();
            this.Loaded += SoruEkle_Loaded1;
        }

        private void anamenudonbutton_Click(object sender, RoutedEventArgs e)
        {
            Frame.Navigate(typeof(HosgeldinPage));
        }

        private async void SoruEkle_Loaded1(object sender, RoutedEventArgs e)
        {
            try
            {
                if (!IsLoaded)
                {
                    var list = await App.APIService.Kategoriler();
                    kategoridetay = JsonConvert.DeserializeObject<List<Kategori>>(JsonConvert.SerializeObject(list.Data));
                    comboBox.ItemsSource = kategoridetay;
                    comboBox.DisplayMemberPath = "KategoriAdi";
                    comboBox.SelectedValuePath = "KategoriID";
                    IsLoaded = true;
                }
                else
                {
                    comboBox.ItemsSource = kategoridetay;
                    comboBox.DisplayMemberPath = "KategoriAdi";
                    comboBox.SelectedValuePath = "KategoriID";
                }
            }
            catch (Exception ex)
            {
                await App.APIService.Log("Soru Ekle Hatası. Detaylar: " + ex.Message);
            }
        }
        int type = 1;
        StorageFile resimfile1, resimfile2;
        CoreApplicationView view;
        bool IsLoaded = false;
        List<Kategori> kategoridetay;

        public async void birinciresim_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                type = 1;
                FileOpenPicker openPicker = new FileOpenPicker();
                openPicker.ViewMode = PickerViewMode.Thumbnail;
                openPicker.SuggestedStartLocation = PickerLocationId.PicturesLibrary;
                openPicker.FileTypeFilter.Add(".jpg");
                openPicker.FileTypeFilter.Add(".jpeg");
                openPicker.FileTypeFilter.Add(".png");
                view = CoreApplication.GetCurrentView();
#if WINDOWS_PHONE_APP
            view.Activated += SoruEkle_Activated;
            openPicker.PickSingleFileAndContinue();
#else
                resimfile1 = await openPicker.PickSingleFileAsync();
#endif
            }
            catch (Exception ex)
            {
                await App.APIService.Log("Birinci Resim Seçim Hatası. Detaylar: " + ex.Message);
            }
        }
        private async void SoruEkle_Activated(CoreApplicationView sender, Windows.ApplicationModel.Activation.IActivatedEventArgs args)
        {
#if WINDOWS_PHONE_APP
            FileOpenPickerContinuationEventArgs param = args as FileOpenPickerContinuationEventArgs;

            if (param != null)
            {
                if (param.Files.Count == 0) return;

                view.Activated -= SoruEkle_Activated;
                if (type == 1){
                    resimfile1 = param.Files[0];
                    await Resim1Degistir(resimfile1);
                }
                else
                {
                    resimfile2 = param.Files[0];
                    await Resim2Degistir(resimfile2);
                }
            }
#endif
        }

        async void Gonder()
        {
            try
            {
                if (!string.IsNullOrEmpty(this.Baslik.Text) && !string.IsNullOrEmpty(this.Aciklama.Text) && resimfile1 != null && resimfile2 != null)
                {
                    progressBar.IsActive = true;
                    var resim1data = await FileHelper.ReadFile(resimfile1);
                    var resim2data = await FileHelper.ReadFile(resimfile2);
                    var result = await App.APIService.SoruEkle(resim1data, resimfile1.Name, resim2data, resimfile2.Name, Baslik.Text, Aciklama.Text, comboBox.SelectedValue.ToString());
                    await Mesaj.MesajGoster(result.Mesaj);
                    Frame.Navigate(typeof(HosgeldinPage));
                    progressBar.IsActive = true;
                }
                else
                {
                    await Mesaj.MesajGoster("Dosya seçilirken hata oluştu");
                }
            }
            catch (Exception ex)
            {
                await App.APIService.Log("Soru Ekle Hatası. Detaylar: " + ex.Message);
            }
        }

        private async void ikinciresim_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                type = 2;
                FileOpenPicker openPicker = new FileOpenPicker();
                openPicker.ViewMode = PickerViewMode.Thumbnail;
                openPicker.SuggestedStartLocation = PickerLocationId.PicturesLibrary;
                openPicker.FileTypeFilter.Add(".jpg");
                openPicker.FileTypeFilter.Add(".jpeg");
                openPicker.FileTypeFilter.Add(".png");

#if WINDOWS_PHONE_APP

            openPicker.PickSingleFileAndContinue();
            view.Activated += SoruEkle_Activated;

#else

                resimfile2 = await openPicker.PickSingleFileAsync();
#endif
            }

            catch (Exception ex)
            {
                await App.APIService.Log("İkinci Resim Seçim Hatası. Detaylar: " + ex.Message);
            }
        }
        private void paylasbutton_Click(object sender, RoutedEventArgs e)
        {
            Gonder();
        }

        private void Geri_Tapped(object sender, TappedRoutedEventArgs e)
        {
            Navigator.CurrentFrame.GoBack();
        }

        async Task Resim1Degistir(StorageFile file)
        {
            try
            {
                birinciresim.Background = null;
                birinciresim.Content = new Ellipse()
                {
                    Height = 150,
                    Width = 150,
                    Fill = new ImageBrush()
                    {
                        ImageSource = await FileHelper.ByteToImage(await FileHelper.ReadFielToByteArray(file))
                    }
                };
            }
            catch (Exception ex)
            {
                await App.APIService.Log("Resim 1 Hatası. Detaylar: " + ex.Message);
            }
        }
        async Task Resim2Degistir(StorageFile file)
        {
            try
            {
                ikinciresim.Background = null;
                ikinciresim.Content = new Ellipse()
                {
                    Height = 150,
                    Width = 150,
                    Fill = new ImageBrush()
                    {
                        ImageSource = await FileHelper.ByteToImage(await FileHelper.ReadFielToByteArray(file))
                    }
                };
            }
            catch (Exception ex)
            {
                await App.APIService.Log("Resim 2 Hatası. Detaylar: " + ex.Message);
            }
        }
    }
}
