using System;
using System.Collections.Generic;
using System.Text;
using Windows.UI.Text;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Media;

namespace OmuBumu.Behaviors
{
    public class PasswordBoxPlaceHolderBehavior : Behavior<PasswordBox>
    {

        public string PlaceHolderText
        {
            get { return (string)GetValue(PlaceHolderTextProperty) + " "; }
            set { SetValue(PlaceHolderTextProperty, value); }
        }

        public static readonly DependencyProperty PlaceHolderTextProperty =
            DependencyProperty.Register("PlaceHolderText", typeof(string), typeof(PasswordBoxPlaceHolderBehavior), new PropertyMetadata(String.Empty));

        public FontStyle PlaceHolderTextFontStyle
        {
            get { return (FontStyle)GetValue(PlaceHolderTextFontStyleProperty); }
            set { SetValue(PlaceHolderTextFontStyleProperty, value); }
        }

        public static readonly DependencyProperty PlaceHolderTextFontStyleProperty =
            DependencyProperty.Register("PlaceHolderTextFontStyle", typeof(FontStyle), typeof(PasswordBoxPlaceHolderBehavior), new PropertyMetadata(FontStyle.Normal));

        public Brush PlaceHolderTextForeground
        {
            get { return (Brush)GetValue(PlaceHolderTextForegroundProperty); }
            set { SetValue(PlaceHolderTextForegroundProperty, value); }
        }

        public static readonly DependencyProperty PlaceHolderTextForegroundProperty =
            DependencyProperty.Register("PlaceHolderTextForeground", typeof(Brush), typeof(PasswordBoxPlaceHolderBehavior), new PropertyMetadata(null));

        private FontStyle _defaultFontStyle { get; set; }
        private Brush _defaultForeground { get; set; }

        protected override void OnAttached()
        {
            AssociatedObject.Loaded += AssociatedObject_Loaded;
            AssociatedObject.Unloaded += AssociatedObject_Unloaded;
        }

        protected override void OnDetached()
        {
            AssociatedObject.Loaded -= AssociatedObject_Loaded;
            AssociatedObject.Unloaded -= AssociatedObject_Unloaded;
        }

        void AssociatedObject_Loaded(object sender, RoutedEventArgs e)
        {
            AssociatedObject.GotFocus += AssociatedObject_GotFocus;
            AssociatedObject.LostFocus += AssociatedObject_LostFocus;

            if (PlaceHolderTextForeground == null)
            {
                PlaceHolderTextForeground = AssociatedObject.Foreground;
            }

            _defaultFontStyle = AssociatedObject.FontStyle;
            _defaultForeground = AssociatedObject.Foreground;

            SetTextBoxBackToPlaceHolderInput();
        }

        void AssociatedObject_Unloaded(object sender, RoutedEventArgs e)
        {
            AssociatedObject.GotFocus -= AssociatedObject_GotFocus;
            AssociatedObject.LostFocus -= AssociatedObject_LostFocus;
        }

        void AssociatedObject_GotFocus(object sender, RoutedEventArgs e)
        {
            PrepareForNormalTextInput();
        }

        void AssociatedObject_LostFocus(object sender, RoutedEventArgs e)
        {
            SetTextBoxBackToPlaceHolderInput();
        }

        private void PrepareForNormalTextInput()
        {
            AssociatedObject.FontStyle = _defaultFontStyle;
            AssociatedObject.Foreground = _defaultForeground;

            if (AssociatedObject.Password == PlaceHolderText)
            {
                AssociatedObject.Password = "";
            }
        }

        private void SetTextBoxBackToPlaceHolderInput()
        {
            if (AssociatedObject.Password == "")
            {
                AssociatedObject.FontStyle = PlaceHolderTextFontStyle;
                AssociatedObject.Password = PlaceHolderText;
                AssociatedObject.Foreground = PlaceHolderTextForeground;
            }
        }
    }

}
