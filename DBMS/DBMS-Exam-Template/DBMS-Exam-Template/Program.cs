using System.Windows.Forms;

namespace DBMS_Exam_Template {
    internal static class Program {
        public static void Main() {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new MainForm());
        }
    }
}