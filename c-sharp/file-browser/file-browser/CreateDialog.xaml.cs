using System.Windows;
using System.IO;
using System.Text.RegularExpressions;

namespace file_browser {
    public partial class CreateDialog : Window {
        public bool correct;
        public string path;
        public CreateDialog(string folderPath) {
            InitializeComponent();
            path = folderPath;
            correct = false;
        }

        private void OKClick(object sender, RoutedEventArgs e) {
            bool isFile = (bool)radioFile.IsChecked;
            bool isDirectory = (bool)radioDirectory.IsChecked;
            if (isFile && !Regex.IsMatch(textName.Text, "^[a-zA-Z0-9_~-]{1,8}(.txt|.php|.html)$")) {
                MessageBox.Show("Wrong name!", "Alert", MessageBoxButton.OK, MessageBoxImage.Information);
            }
            else {
                string name = textName.Text;
                path = path + "\\" + name;
                FileAttributes attributes = FileAttributes.Normal;
                if ((bool)checkA.IsChecked) {
                    attributes |= FileAttributes.Archive;
                }
                    
                if ((bool)checkR.IsChecked) {
                    attributes |= FileAttributes.ReadOnly;
                }
                    
                if ((bool)checkH.IsChecked) {
                    attributes |= FileAttributes.Hidden;
                }
                    
                if ((bool)checkS.IsChecked) {
                    attributes |= FileAttributes.System;
                }
                    
                if (isFile) {
                    File.Create(path);
                }
                else if (isDirectory) {
                    Directory.CreateDirectory(path);
                }
                File.SetAttributes(path, attributes);
                Close();
                correct = true;
            }
        }

        private void CancelClick(object sender, RoutedEventArgs e) {
            Close();
        }
    }
}
