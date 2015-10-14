using System;
using System.Collections.Generic;
using System.Text;

namespace OmuBumu.Common.Types
{
    class Yorumlar
    {
        public string YorumID { get; set; }
        public string UyeID { get; set; }
        public string SoruID { get; set; }
        public string Yorum { get; set; }
        public object Resim { get; set; }
        public string SaveDate { get; set; }
        public string AdiSoyadi { get; set; }
        public string KullaniciAdi { get; set; }
        public string ProfileImage { get; set; }
    }
}
