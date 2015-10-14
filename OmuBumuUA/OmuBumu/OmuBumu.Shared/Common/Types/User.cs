
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace OmuBumu.Common.Types
{
    public class User
    {
        [JsonProperty("UyeID")]
        public string UyeID { get; set; }

        [JsonProperty("KullaniciAdi")]
        public string KullaniciAdi { get; set; }

        [JsonProperty("AdiSoyadi")]
        public string AdiSoyadi { get; set; }

        [JsonProperty("Email")]
        public string Email { get; set; }

        [JsonProperty("ProfileImage")]
        public string ProfileImage { get; set; }

        public User()
        {

        }

        public User(string _UyeID, string _KullaniciAdi, string _AdiSoyadi, string _Email, string _ProfileImage)
        {
            this.UyeID = _UyeID;
            this.KullaniciAdi = _KullaniciAdi;
            this.AdiSoyadi = _AdiSoyadi;
            this.Email = _Email;
            this.ProfileImage = _ProfileImage;
        }
    }
}
