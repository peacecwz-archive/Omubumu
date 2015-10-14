using Newtonsoft.Json;
using OmuBumu.Common;
using OmuBumu.Common.Types;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using Windows.Storage;
using Windows.UI.Popups;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Input;

namespace OmuBumu
{
    public sealed partial class GirisPage : Page
    {

        public static User Uye;

        private static bool IsCreated = false;

        public GirisPage()
        {
            this.InitializeComponent();
            this.Loaded += GirisPage_Loaded;
#if WINDOWS_PHONE_APP
            if (!IsCreated)
                Windows.Phone.UI.Input.HardwareButtons.BackPressed += HardwareButtons_BackPressed;
#endif
        }

        private async void GirisPage_Loaded(object sender, RoutedEventArgs e)
        {
            txtKullaniciAdi.Text = SettingsHelper.GetSetting("KullaniciAdi");
            txtSifre.Password = SettingsHelper.GetSetting("Sifre");
            bool BeniHatirla = false;
            if (bool.TryParse(SettingsHelper.GetSetting("BeniHatirla"), out BeniHatirla) && BeniHatirla)
                await Giris();
        }

        private void kayitolbutton_Click(object sender, RoutedEventArgs e)
        {
            Frame.Navigate(typeof(KayitOlPage));
        }

        private async void Button_Click(object sender, RoutedEventArgs e)
        {
            await Giris();
        }

        async Task Giris()
        {
            try
            {
                if (string.IsNullOrEmpty(txtKullaniciAdi.Text) | string.IsNullOrEmpty(txtSifre.Password))
                {
                    await Mesaj.MesajGoster("Lütfen Kullanıcı Adınızı ve Şifrenizi Yazınız!");
                }
                progressBar.IsActive = true;
                Uye = null;
                App.APIService = new DataClient();
                var giris = await App.APIService.Giris(txtKullaniciAdi.Text, txtSifre.Password);
                if (giris != null && giris.Sonuc)
                {
                    SettingsHelper.SaveSetting("KullaniciAdi", txtKullaniciAdi.Text);
                    SettingsHelper.SaveSetting("Sifre", txtSifre.Password);
                    SettingsHelper.SaveSetting("BeniHatirla", true.ToString());
                    Uye = JsonConvert.DeserializeObject<User>(JsonConvert.SerializeObject(giris.Data));
                    Navigator.CurrentFrame.Navigate(typeof(HosgeldinPage), Uye);
                }
                else
                {
                    await Mesaj.MesajGoster(giris.Mesaj); //geldim
                }
            }
            catch (Exception ex)
            {
                await App.APIService.Log("Giriş Sayfası Login Hatası Detaylar: " + ex.Message);
            }
            finally
            {
                progressBar.IsActive = false;
            }
        }

        private void uyeolbutton_Click(object sender, RoutedEventArgs e)
        {
            Frame.Navigate(typeof(KayitOlPage));
        }

        private void SifremiUnuttum_Click(object sender, RoutedEventArgs e)
        {
            Frame.Navigate(typeof(SifremiUnuttum));
        }

        public static async Task CikisYap()
        {
            Uye = null;
            await App.APIService.Cikis();
            SettingsHelper.SaveSetting("KullaniciAdi", null);
            SettingsHelper.SaveSetting("Sifre", null);
            SettingsHelper.SaveSetting("BeniHatirla", null);
            while (Navigator.CurrentFrame.CanGoBack)
                Navigator.CurrentFrame.GoBack();
        }

#if WINDOWS_PHONE_APP
        async void HardwareButtons_BackPressed(object sender, Windows.Phone.UI.Input.BackPressedEventArgs e)
        {
            IsCreated = true;
            if (Navigator.CurrentFrame.CanGoBack && !Navigator.CurrentFrame.CurrentSourcePageType.Equals(typeof(HosgeldinPage)))
            {
                e.Handled = true;
                Navigator.CurrentFrame.GoBack();
            }
            else if (!Navigator.CurrentFrame.CanGoBack & Navigator.CurrentFrame.CurrentSourcePageType.Equals(typeof(GirisPage)))
            {
                e.Handled = false;
            }
            else
            {
                try
                {
                    e.Handled = true;
                    App.Current.Exit();
                }
                catch
                {
                    e.Handled = true;
                }
            }
        }
#endif

        private async void Text_KeyDown(object sender, KeyRoutedEventArgs e)
        {
            if (e.Key == Windows.System.VirtualKey.Enter)
            {
                if (sender is TextBox)
                {
                    txtSifre.Focus(FocusState.Programmatic);
                }
                else if (sender is PasswordBox)
                {
                    await Giris();
                }
            }
        }
    }
}
