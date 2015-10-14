using System;
using System.Collections.Generic;
using System.Text;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;

namespace OmuBumu
{
    public class Navigator
    {
        public static Frame CurrentFrame { get; set; }

        static Navigator()
        {
            CurrentFrame = Window.Current.Content as Frame;
            if (CurrentFrame == null)
                CurrentFrame = new Frame();
        }
    }
}
