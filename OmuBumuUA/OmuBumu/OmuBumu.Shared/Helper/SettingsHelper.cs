using System;
using System.Collections.Generic;
using System.Text;
using Windows.Storage;

namespace OmuBumu
{
    public class SettingsHelper
    {
        public static void SaveSetting(string key,string value)
        {
            ApplicationData.Current.LocalSettings.Values[key] = value;
        }

        public static string GetSetting(string key)
        {
            return (ApplicationData.Current.LocalSettings.Values.ContainsKey(key)) ? ApplicationData.Current.LocalSettings.Values[key].ToString() : "";
        }
    }
}
