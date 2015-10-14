using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using OmuBumu.Common.Types;
using Newtonsoft.Json;
using System.Net;
using OmuBumu.Helper;
using Windows.Security.ExchangeActiveSyncProvisioning;
using Windows.Web.Http.Filters;

namespace OmuBumu.Common
{
    public class DataClient
    {
        private HttpClient Client;
        private HttpClientHandler Handler;
        private CookieContainer Cookies = new CookieContainer();

        public DataClient()
        {
            Handler = new HttpClientHandler()
            {
                CookieContainer = Cookies,
                ClientCertificateOptions = ClientCertificateOption.Automatic
            };
            Client = new HttpClient(Handler);
            Client.BaseAddress = new Uri("http://omubumuapp.com/");

            EasClientDeviceInformation deviceInfo = new EasClientDeviceInformation();
            string userAgent = "FriendlyName={0};OperatingSystem={1};SystemManufacturer={2};SystemProductName={3};SystemSku={4};";
            userAgent = String.Format(userAgent, deviceInfo.FriendlyName, deviceInfo.OperatingSystem, deviceInfo.SystemManufacturer, deviceInfo.SystemProductName, deviceInfo.SystemSku);
            Client.DefaultRequestHeaders.TryAddWithoutValidation("User-Agent", userAgent);
        }

        public async Task<ResultContext> Giris(string kullaniciAdi, string Sifre)
        {
            Cookies = new CookieContainer();
            Dictionary<string, string> form = new Dictionary<string, string>();
            form.Add("KullaniciAdi", DataEncrypter.Encrypt(kullaniciAdi));
            form.Add("Sifre", DataEncrypter.Encrypt(Sifre));
            var sonucKumesi = await Client.PostAsync("/api/Giris", new FormUrlEncodedContent(form));
            if (sonucKumesi != null && sonucKumesi.Content != null)
            {
                var sonuc = await sonucKumesi.Content.ReadAsStringAsync();
                if (sonuc != null)
                {
                    return JsonConvert.DeserializeObject<ResultContext>(DataEncrypter.Decrypt(sonuc));
                }
            }
            return new ResultContext()
            {
                Sonuc = false,
                Mesaj = "İstek yaparken hata oluştu"
            };
        }

        public async Task<ResultContext> Cikis()
        {
            Dictionary<string, string> form = new Dictionary<string, string>();
            var sonucKumesi = await Client.PostAsync("/api/Cikis", new FormUrlEncodedContent(form));
            if (sonucKumesi != null && sonucKumesi.Content != null)
            {
                var sonuc = await sonucKumesi.Content.ReadAsStringAsync();
                if (sonuc != null)
                {
                    return JsonConvert.DeserializeObject<ResultContext>(DataEncrypter.Decrypt(sonuc));
                }
            }
            return new ResultContext()
            {
                Sonuc = false,
                Mesaj = "İstek yaparken hata oluştu"
            };
        }

        public async Task<ResultContext> ProfilGuncelle(string AdiSoyadi, string Email, string sifre)
        {
            Dictionary<string, string> form = new Dictionary<string, string>();
            form.Add("Sifre", DataEncrypter.Encrypt(sifre));
            form.Add("AdiSoyadi", DataEncrypter.Encrypt(AdiSoyadi));
            form.Add("Email", DataEncrypter.Encrypt(Email));
            var sonucKumesi = await Client.PostAsync("/api/ProfilGuncelle", new FormUrlEncodedContent(form));
            if (sonucKumesi != null && sonucKumesi.Content != null)
            {
                var sonuc = await sonucKumesi.Content.ReadAsStringAsync();
                if (sonuc != null)
                {
                    return JsonConvert.DeserializeObject<ResultContext>(DataEncrypter.Decrypt(sonuc));
                }
            }
            return new ResultContext()
            {
                Sonuc = false,
                Mesaj = "İstek yaparken hata oluştu"
            };
        }

        public async Task<ResultContext> Kayit(string kullaniciAdi, string Sifre, string AdiSoyadi, string Email)
        {
            Dictionary<string, string> form = new Dictionary<string, string>();
            form.Add("KullaniciAdi", DataEncrypter.Encrypt(kullaniciAdi));
            form.Add("Sifre", DataEncrypter.Encrypt(Sifre));
            form.Add("AdiSoyadi", DataEncrypter.Encrypt(AdiSoyadi));
            form.Add("Email", DataEncrypter.Encrypt(Email));
            var sonucKumesi = await Client.PostAsync("/api/Kayit", new FormUrlEncodedContent(form));
            if (sonucKumesi != null && sonucKumesi.Content != null)
            {
                var sonuc = await sonucKumesi.Content.ReadAsStringAsync();
                if (sonuc != null)
                {
                    return JsonConvert.DeserializeObject<ResultContext>(DataEncrypter.Decrypt(sonuc));
                }
            }
            return new ResultContext()
            {
                Sonuc = false,
                Mesaj = "İstek yaparken hata oluştu"
            };
        }

        public async Task<ResultContext> SifremiUnuttum(string mail)
        {
            Cookies = new CookieContainer();
            Dictionary<string, string> form = new Dictionary<string, string>();
            form.Add("Email", DataEncrypter.Encrypt(mail));
            var sonucKumesi = await Client.PostAsync("/api/SifremiUnuttum", new FormUrlEncodedContent(form));
            if (sonucKumesi != null && sonucKumesi.Content != null)
            {
                var sonuc = await sonucKumesi.Content.ReadAsStringAsync();
                if (sonuc != null)
                {
                    return JsonConvert.DeserializeObject<ResultContext>(DataEncrypter.Decrypt(sonuc));
                }
            }
            return new ResultContext()
            {
                Sonuc = false,
                Mesaj = "İstek yaparken hata oluştu"
            };

        }

        public async Task<ResultContext> ResimGuncelle(byte[] Resim, string DosyaAdi)
        {
            var content = new MultipartFormDataContent();
            content.Add(new ByteArrayContent(Resim, 0, Resim.Length), "Resim", DosyaAdi);
            var sonucKumesi = await Client.PostAsync("/api/ResimGuncelle", content);
            if (sonucKumesi != null && sonucKumesi.Content != null)
            {
                var sonuc = await sonucKumesi.Content.ReadAsStringAsync();
                if (sonuc != null)
                {
                    return JsonConvert.DeserializeObject<ResultContext>(DataEncrypter.Decrypt(sonuc));
                }
            }
            return new ResultContext()
            {
                Sonuc = false,
                Mesaj = "İstek yaparken hata oluştu"
            };
        }

        public async Task<ResultContext> SoruEkle(byte[] Resim1, string Resim1Adi, byte[] Resim2, string Resim2Adi, string Baslik, string Aciklama, string KategoriID)
        {
            var content = new MultipartFormDataContent();
            content.Add(new ByteArrayContent(Resim1, 0, Resim1.Length), "Resim1", Resim1Adi);
            content.Add(new ByteArrayContent(Resim2, 0, Resim2.Length), "Resim2", Resim2Adi);

            content.Add(new StringContent(DataEncrypter.Encrypt(Baslik)), "Baslik");
            content.Add(new StringContent(DataEncrypter.Encrypt(Aciklama)), "Aciklama");
            content.Add(new StringContent(DataEncrypter.Encrypt(KategoriID)), "KategoriID");

            var sonucKumesi = await Client.PostAsync("/api/SoruEkle", content);
            if (sonucKumesi != null && sonucKumesi.Content != null)
            {
                var sonuc = await sonucKumesi.Content.ReadAsStringAsync();
                if (sonuc != null)
                {
                    return JsonConvert.DeserializeObject<ResultContext>(DataEncrypter.Decrypt(sonuc));
                }
            }
            return new ResultContext()
            {
                Sonuc = false,
                Mesaj = "İstek yaparken hata oluştu"
            };
        }

        public async Task<ResultContext> Sorular(string KategoriID = "", string SoruID = "", string UyeID = "")
        {
            string url = "/api/Sorular";
            if (!string.IsNullOrEmpty(KategoriID) && !string.IsNullOrEmpty(UyeID))
            {
                url += "?UyeID=" + DataEncrypter.Encrypt(UyeID);
                url += "&KategoriID=" + DataEncrypter.Encrypt(KategoriID);
            }
            else if (!string.IsNullOrEmpty(KategoriID))
            {
                url += "?KategoriID=" + DataEncrypter.Encrypt(KategoriID);
            }
            else if (!string.IsNullOrEmpty(SoruID))
            {
                url += "?SoruID=" + DataEncrypter.Encrypt(SoruID);
            }
            else if (!string.IsNullOrEmpty(UyeID))
            {
                url += "?UyeID=" + DataEncrypter.Encrypt(UyeID);
            }
            var sonucKumesi = await Client.GetAsync(url);
            if (sonucKumesi != null && sonucKumesi.Content != null)
            {
                var sonuc = await sonucKumesi.Content.ReadAsStringAsync();
                if (sonuc != null)
                {
                    return JsonConvert.DeserializeObject<ResultContext>(DataEncrypter.Decrypt(sonuc));
                }
            }
            return new ResultContext()
            {
                Sonuc = false,
                Mesaj = "İstek yaparken hata oluştu"
            };
        }

        public async Task<ResultContext> Oyla(string SoruID, bool ResimNo)
        {
            Dictionary<string, string> form = new Dictionary<string, string>();
            form.Add("SoruID", DataEncrypter.Encrypt(SoruID));
            if (ResimNo)
                form.Add("ResimNo", DataEncrypter.Encrypt("1"));

            else
                form.Add("ResimNo", DataEncrypter.Encrypt("0"));

            var sonucKumesi = await Client.PostAsync("/api/Oyla", new FormUrlEncodedContent(form));
            if (sonucKumesi != null && sonucKumesi.Content != null)
            {
                var sonuc = await sonucKumesi.Content.ReadAsStringAsync();
                if (sonuc != null)
                {
                    return JsonConvert.DeserializeObject<ResultContext>(DataEncrypter.Decrypt(sonuc));
                }
            }
            return new ResultContext()
            {
                Sonuc = false,
                Mesaj = "İstek yaparken hata oluştu"
            };
        }

        public async Task<ResultContext> Oylar(string SoruID)
        {
            string url = "/api/Oylar";
            if (!string.IsNullOrEmpty(SoruID))
            {
                url += "?SoruID=" + DataEncrypter.Encrypt(SoruID);
            }
            var sonucKumesi = await Client.GetAsync(url);
            if (sonucKumesi != null && sonucKumesi.Content != null)
            {
                var sonuc = await sonucKumesi.Content.ReadAsStringAsync();
                if (sonuc != null)
                {
                    return JsonConvert.DeserializeObject<ResultContext>(DataEncrypter.Decrypt(sonuc));
                }
            }
            return new ResultContext()
            {
                Sonuc = false,
                Mesaj = "İstek yaparken hata oluştu"
            };
        }

        public async Task<ResultContext> Yorumlar(string SoruID)
        {
            string url = "/api/Yorumlar";
            if (!string.IsNullOrEmpty(SoruID))
            {
                url += "?SoruID=" + DataEncrypter.Encrypt(SoruID);
            }
            var sonucKumesi = await Client.GetAsync(url);
            if (sonucKumesi != null && sonucKumesi.Content != null)
            {
                var sonuc = await sonucKumesi.Content.ReadAsStringAsync();
                if (sonuc != null)
                {
                    return JsonConvert.DeserializeObject<ResultContext>(DataEncrypter.Decrypt(sonuc));
                }
            }
            return new ResultContext()
            {
                Sonuc = false,
                Mesaj = "İstek yaparken hata oluştu"
            };
        }

        public async Task<ResultContext> YorumEkle(string SoruID, string Yorum, byte[] YorumResim, string YorumResimAdi)
        {
            var content = new MultipartFormDataContent();

            content.Add(new StringContent(DataEncrypter.Encrypt(SoruID)), "SoruID");
            content.Add(new StringContent(DataEncrypter.Encrypt(Yorum)), "Yorum");
            if (YorumResim != null)
            {
                content.Add(new ByteArrayContent(YorumResim, 0, YorumResim.Length), "Resim", YorumResimAdi);

            }

            var sonucKumesi = await Client.PostAsync("/api/YorumEkle", content);
            if (sonucKumesi != null && sonucKumesi.Content != null)
            {
                var sonuc = await sonucKumesi.Content.ReadAsStringAsync();
                if (sonuc != null)
                {
                    return JsonConvert.DeserializeObject<ResultContext>(DataEncrypter.Decrypt(sonuc));
                }
            }
            return new ResultContext()
            {
                Sonuc = false,
                Mesaj = "İstek yaparken hata oluştu"
            };

        }

        public async Task<ResultContext> Kategoriler()
        {
            string url = "/api/Kategoriler";
            var sonucKumesi = await Client.GetAsync(url);
            if (sonucKumesi != null && sonucKumesi.Content != null)
            {
                var sonuc = await sonucKumesi.Content.ReadAsStringAsync();
                if (sonuc != null)
                {
                    return JsonConvert.DeserializeObject<ResultContext>(DataEncrypter.Decrypt(sonuc));
                }
            }
            return new ResultContext()
            {
                Sonuc = false,
                Mesaj = "İstek yaparken hata oluştu"
            };
        }

        public async Task<ResultContext> UyeProfil(string UyeID)
        {
            string url = "/api/UyeProfil?UyeID=" + DataEncrypter.Encrypt(UyeID);
            var sonucKumesi = await Client.GetAsync(url);
            if (sonucKumesi != null && sonucKumesi.Content != null)
            {
                var sonuc = await sonucKumesi.Content.ReadAsStringAsync();
                if (sonuc != null)
                {
                    return JsonConvert.DeserializeObject<ResultContext>(DataEncrypter.Decrypt(sonuc));
                }
            }
            return new ResultContext()
            {
                Sonuc = false,
                Mesaj = "İstek yaparken hata oluştu"
            };

        }

        public async Task Log(string Mesaj)
        {
            try
            {
                Dictionary<string, string> form = new Dictionary<string, string>();
                form.Add("Mesaj", DataEncrypter.Encrypt(Mesaj));
                var sonucKumesi = await Client.PostAsync("/api/Log", new FormUrlEncodedContent(form));
            }
            catch
            {
                App.Current.Exit();
            }
        }
    }
}

