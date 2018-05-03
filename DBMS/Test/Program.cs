using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Windows.Forms;

namespace Lab1
{
    internal class Lab11 : Form
    {
        private DataGridView _dataGridViewParent;
        private DataGridView _dataGridViewChild;
        private SqlDataAdapter _dataAdapter;
        private DataSet _dataSet;

        public Lab11()
        {
            InitializeComponent();
        }

        private void InitializeComponent()
        {
            var con = new SqlConnection(
                "Data Source=zario.go.ro;Initial Catalog=airports;Integrated Security=False;User ID=sa;Password=Jinars777;Pooling=False");
            con.Open();
            _dataGridViewParent = new DataGridView();
            _dataGridViewChild = new DataGridView();
            _dataGridViewParent.Size = new Size(600, 400);
            _dataGridViewChild.Size = new Size(600, 400);
            _dataGridViewChild.Location = new Point(600,0);
            Controls.Add(_dataGridViewParent);
            Controls.Add(_dataGridViewChild);
            _dataAdapter = new SqlDataAdapter();
            _dataAdapter.SelectCommand = new SqlCommand("SELECT * FROM airports.dbo.Pilots;", con);
            _dataSet = new DataSet();
            _dataSet.Clear();
            _dataAdapter.Fill(_dataSet);
            _dataGridViewParent.DataSource = _dataSet.Tables[0];
        }
    }

    internal static class Program
    {
        public static void Main(string[] args)
        {
            var da = new Lab11();
            Application.Run(da);
        }        
    }
}