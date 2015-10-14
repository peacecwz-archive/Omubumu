using Newtonsoft.Json;
using OmuBumu.Common;
using OmuBumu.Common.Types;
using OmuBumu.Helper;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using Windows.ApplicationModel.Activation;
using Windows.ApplicationModel.Core;
using Windows.Storage;
using Windows.Storage.Pickers;
using Windows.UI.Popups;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Shapes;

namespace OmuBumu
{
    public sealed partial class ProfilGuncelle : Page
    {
        CoreApplicationView view;
        StorageFile file;
        int DeviceType = 1;
        public ProfilGuncelle()
        {
            this.InitializeComponent();
            Loaded += ProfilGuncelle_Loaded;
        }
        private void geridonbutton_Click(object sender, RoutedEventArgs e)
        {
            Frame.GoBack();
        }

        private async void ProfilGuncelle_Loaded(object sender, RoutedEventArgs e)
        {
            adsoyad.Text = GirisPage.Uye.AdiSoyadi;
            email.Text = GirisPage.Uye.Email;
            profileImage.Content = new Ellipse()
            {
                Height = 150,
                Width = 150,
                Fill = new ImageBrush()
                {
                    Stretch = Stretch.Fill,
                    ImageSource = await FileHelper.ByteToImage(await FileHelper.GetDataFromUri(new Uri(GirisPage.Uye.ProfileImage, UriKind.Absolute)))
                }
            };
        }

        async Task Guncelle()
        {
            try
            {
                progressBar.IsActive = true;
                var guncellemesonucu = await App.APIService.ProfilGuncelle(adsoyad.Text, email.Text, txtpassword.Password);
                if (guncellemesonucu.Sonuc == true)
                {
                    await Mesaj.MesajGoster(guncellemesonucu.Mesaj);
                    await GirisPage.CikisYap();
                }
                else
                {
                    await Mesaj.MesajGoster(guncellemesonucu.Mesaj);
                }

            }
            catch (Exception ex)
            {
                await App.APIService.Log("Profil Güncelleme Hatası. Detaylar: " + ex.Message);
            }
            finally
            {
                progressBar.IsActive = false;
            }
        }

        private async void Guncellebutton_Click(object sender, RoutedEventArgs e)
        {
            await Guncelle();
        }

        private async void ProfileResimGuncelle_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                await Mesaj.Soru("Profil Resminizi Güncellemek İstiyor Musunuz?", async (sndr) =>
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
            DeviceType = 1;
            openPicker.PickSingleFileAndContinue();
#else
                    DeviceType = 2;
                    file = await openPicker.PickSingleFileAsync();
#endif
                    if (DeviceType == 2 && file != null)
                    {
                        var data = await FileHelper.ReadFile(file);
                        var result = await App.APIService.ResimGuncelle(data, file.Name);
                        if (result != null)
                            await Mesaj.MesajGoster("Dosya seçilirken hata oluştu");
                    }
                    else if (DeviceType == 2)
                    {
                        await Mesaj.MesajGoster("Dosya seçilirken hata oluştu");
                    }
                });
            }
            catch (Exception ex)
            {
                await App.APIService.Log("Profil Resim Güncelleme Hatası. Detaylar: " + ex.Message);
            }
        }

        private async void SoruDetay_Activated(CoreApplicationView sender, Windows.ApplicationModel.Activation.IActivatedEventArgs args)
        {
#if WINDOWS_PHONE_APP
            FileOpenPickerContinuationEventArgs param = args as FileOpenPickerContinuationEventArgs;

            if (param != null)
            {
                if (param.Files.Count == 0) return;
                view.Activated -= SoruDetay_Activated;
                file = param.Files[0];
                if(file!=null){
                var data = await FileHelper.ReadFile(file);
                        var result = await App.APIService.ResimGuncelle(data, file.Name);
                        if (result != null)
                            await Mesaj.MesajGoster(result.Mesaj);
                }
            }
#endif
        }

        private void Geri_Tapped(object sender, TappedRoutedEventArgs e)
        {
            Navigator.CurrentFrame.GoBack();
        }

        private async void Text_KeyDown(object sender, KeyRoutedEventArgs e)
        {
            if (e.Key == Windows.System.VirtualKey.Enter)
            {
                if (sender is TextBox && ((TextBox)sender).Name == "email")
                {
                    adsoyad.Focus(FocusState.Programmatic);
                }
                else if (sender is TextBox && ((TextBox)sender).Name == "adsoyad")
                {
                    txtpassword.Focus(FocusState.Programmatic);
                }

                else if (sender is PasswordBox && ((PasswordBox)sender).Name == "txtpassword")
                {
                    txtcopassword.Focus(FocusState.Programmatic);
                }
                else if (sender is PasswordBox && ((PasswordBox)sender).Name == "txtcopassword")
                {
                    await Guncelle();
                }
            }
        }

    }
}
