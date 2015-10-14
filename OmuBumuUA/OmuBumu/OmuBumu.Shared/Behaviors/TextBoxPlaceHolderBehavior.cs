using System;
using System.Collections.Generic;
using System.Text;
using Windows.UI.Text;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Media;

namespace OmuBumu.Behaviors
{
    public class TextBoxPlaceHolderBehavior : Behavior<TextBox>
    {

        public string PlaceHolderText
        {
            get { return (string)GetValue(PlaceHolderTextProperty) + " "; }
            set { SetValue(PlaceHolderTextProperty, value); }
        }

        public static readonly DependencyProperty PlaceHolderTextProperty =
            DependencyProperty.Register("PlaceHolderText", typeof(string), typeof(TextBoxPlaceHolderBehavior), new PropertyMetadata(String.Empty));

        public FontStyle PlaceHolderTextFontStyle
        {
            get { return (FontStyle)GetValue(PlaceHolderTextFontStyleProperty); }
            set { SetValue(PlaceHolderTextFontStyleProperty, value); }
        }

        public static readonly DependencyProperty PlaceHolderTextFontStyleProperty =
            DependencyProperty.Register("PlaceHolderTextFontStyle", typeof(FontStyle), typeof(TextBoxPlaceHolderBehavior), new PropertyMetadata(FontStyle.Normal));

        public TextAlignment PlaceHolderTextAlignment
        {
            get { return (TextAlignment)GetValue(PlaceHolderTextAlignmentProperty); }
            set { SetValue(PlaceHolderTextAlignmentProperty, value); }
        }

        public static readonly DependencyProperty PlaceHolderTextAlignmentProperty =
            DependencyProperty.Register("PlaceHolderTextAlignment", typeof(TextAlignment), typeof(TextBoxPlaceHolderBehavior), new PropertyMetadata(TextAlignment.Left));

        public Brush PlaceHolderTextForeground
        {
            get { return (Brush)GetValue(PlaceHolderTextForegroundProperty); }
            set { SetValue(PlaceHolderTextForegroundProperty, value); }
        }

        public static readonly DependencyProperty PlaceHolderTextForegroundProperty =
            DependencyProperty.Register("PlaceHolderTextForeground", typeof(Brush), typeof(TextBoxPlaceHolderBehavior), new PropertyMetadata(null));

        private FontStyle _defaultFontStyle { get; set; }
        private TextAlignment _defaultTextAlignment { get; set; }
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
            _defaultTextAlignment = AssociatedObject.TextAlignment;
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
            AssociatedObject.TextAlignment = _defaultTextAlignment;
            AssociatedObject.Foreground = _defaultForeground;

            if (AssociatedObject.Text == PlaceHolderText)
            {
                AssociatedObject.Text = "";
            }
        }

        private void SetTextBoxBackToPlaceHolderInput()
        {
            if (AssociatedObject.Text == "")
            {
                AssociatedObject.FontStyle = PlaceHolderTextFontStyle;
                AssociatedObject.TextAlignment = PlaceHolderTextAlignment;
                AssociatedObject.Text = PlaceHolderText;
                AssociatedObject.Foreground = PlaceHolderTextForeground;
            }
        }
    }

}
