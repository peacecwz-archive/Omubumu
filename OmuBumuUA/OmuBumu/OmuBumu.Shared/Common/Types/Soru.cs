using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace OmuBumu.Common.Types
{
    public class Soru
    {
        [JsonProperty("SoruID")]
        public string SoruID { get; set; }
        [JsonProperty("UyeID")]
        public string UyeID { get; set; }
        [JsonProperty("Resim1")]
        public string Resim1 { get; set; }
        [JsonProperty("Resim2")]
        public string Resim2 { get; set; }
        [JsonProperty("Aciklama")]
        public string Aciklama { get; set; }
        [JsonProperty("Baslik")]
        public string Baslik { get; set; }
        [JsonProperty("KategoriID")]
        public string KategoriID { get; set; }
        [JsonProperty("SaveDate")]
        public string SaveDate { get; set; }
        [JsonProperty("KategoriAdi")]
        public string KategoriAdi { get; set; }
        [JsonProperty("AdiSoyadi")]
        public string AdiSoyadi { get; set; }
        [JsonProperty("KullaniciAdi")]
        public string KullaniciAdi { get; set; }
        [JsonProperty("ProfileImage")]
        public string ProfileImage { get; set; }
        public Soru()
        {

        }

        public Soru(string _SoruID,string _UyeID,string _Resim1,string _Resim2,string _Aciklama , string _Baslik , string _KategoriID , string _SaveDate , string _KategoriAdi , string _AdiSoyadi , string _KullaniciAdi , string _ProfileImage)

        {
            //  this.UyeID = _UyeID;
            SoruID = _SoruID;
            UyeID = _UyeID;
            Resim1 = _Resim1;
            Resim2 = _Resim2;
            Aciklama = _Aciklama;
            Baslik = _Baslik;
            KategoriID = _KategoriID;
            SaveDate = _SaveDate;
            KategoriAdi = _KategoriAdi;
            AdiSoyadi = _AdiSoyadi;
            KullaniciAdi = _KullaniciAdi;
            ProfileImage = _ProfileImage;
        }
    }
}
