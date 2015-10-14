using OmuBumu.Common;
using OmuBumu.Common.Types;
using System;
using System.Collections.Generic;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Popups;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Input;


namespace OmuBumu
{

    public sealed partial class KayitOlPage : Page
    {

        public KayitOlPage()
        {
            this.InitializeComponent();
        }

        async Task KayitOl()
        {
            progressBar.IsActive = true;
            if (kontrol.IsChecked == false)
            {
                await Mesaj.MesajGoster("Lütfen Kullanım Koşullarını Kabul Edin");
                return;
            }
            else if (string.IsNullOrEmpty(this.txtUsername.Text))
            {
                await Mesaj.MesajGoster("Bir Kullanıcı Adı Girmelisiniz");
                return;
            }
            else if (string.IsNullOrEmpty(this.txtPassword.Password.ToString()))
            {
                await Mesaj.MesajGoster("Bir Şifre Belirlemelisiniz");
                return;
            }
            else if (string.IsNullOrEmpty(this.txtConPassword.Password.ToString()))
            {
                await Mesaj.MesajGoster("Lütfen Şifre Doğrulama Kısmını Doldurun");
                return;
            }
            else if (txtPassword.Password.ToString() != this.txtConPassword.Password.ToString())
            {
                await Mesaj.MesajGoster("Şifreleriniz Eşleşmiyor");
                return;
            }
            else if (string.IsNullOrEmpty(this.txtName.Text))
            {
                await Mesaj.MesajGoster("Lütfen Tam Adınızı Girin");
                return;
            }

            else if (string.IsNullOrEmpty(this.txtEmail.Text))
            {
                await Mesaj.MesajGoster("Lütfen Email Adresinizi Girin");
                return;
            }
            try
            {
                var uyeliksonucu = await App.APIService.Kayit(txtUsername.Text, txtPassword.Password, txtName.Text, txtEmail.Text);
                if (uyeliksonucu.Sonuc == true)
                {
                    await Mesaj.MesajGoster(uyeliksonucu.Mesaj);
                    if (Navigator.CurrentFrame.CanGoBack)
                        Navigator.CurrentFrame.GoBack();
                }
                else
                {
                    await Mesaj.MesajGoster(uyeliksonucu.Mesaj);
                }
            }
            catch (Exception ex)
            {
               await App.APIService.Log("Kayıt Hatası. Detaylar: " + ex.Message);
            }
            finally
            {
                progressBar.IsActive = false;
            }
        }

        private async void KayitOlButton_Click(object sender, RoutedEventArgs e)
        {
            await KayitOl();
        }

        private void Geri_Tapped(object sender, TappedRoutedEventArgs e)
        {
            Navigator.CurrentFrame.GoBack();
        }

        private void GeriButton_Click(object sender, RoutedEventArgs e)
        {
            Navigator.CurrentFrame.GoBack();
        }

        private async void Text_KeyDown(object sender, KeyRoutedEventArgs e)
        {
            if (e.Key == Windows.System.VirtualKey.Enter)
            {
                if (sender is TextBox && ((TextBox)sender).Name == "txtUsername")
                {
                    txtName.Focus(FocusState.Programmatic);
                }
                else if (sender is TextBox && ((TextBox)sender).Name == "txtName")
                {
                    txtEmail.Focus(FocusState.Programmatic);
                }

                else if (sender is TextBox && ((TextBox)sender).Name == "txtEmail")
                {
                    txtPassword.Focus(FocusState.Programmatic);
                }

                else if (sender is PasswordBox && ((PasswordBox)sender).Name == "txtPassword")
                {
                    txtConPassword.Focus(FocusState.Programmatic);
                }
                else if (sender is PasswordBox && ((PasswordBox)sender).Name == "txtConPassword")
                {
                    kontrol.Focus(FocusState.Programmatic);
                }
                else if(sender is CheckBox)
                {
                    await KayitOl();
                }
            }
        }
    }
}
