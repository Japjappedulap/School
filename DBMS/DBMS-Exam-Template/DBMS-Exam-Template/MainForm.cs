using System;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Windows.Forms;

namespace DBMS_Exam_Template {
    public sealed class MainForm : Form {
        // templated
        private const string ConnectionString =
            "Data Source=Vlad;" + "Initial Catalog=Exam;" + "Integrated Security=True;";

        private SqlDataAdapter _childAdapter;

        private DataGridView _dataGridViewChild;
        private DataGridView _dataGridViewParent;
        private DataSet _dataSet;

        private SqlDataAdapter _parentAdapter;
        private TextBox _searchTextBox;
        private Button _synchronizeButton;

        public MainForm() {
            InitializeGui();
            PopulateParent();
        }

        private void PopulateParent(string argument = "") {
            // templated
            const string table = "Exam.dbo.Parent";
            const string fieldForMatching = "ParentID";
            // 
            using (var con = new SqlConnection(ConnectionString)) {
                try {
                    con.Open();
                    var sqlString = "SELECT * FROM " +
                                    table + ' ' +
                                    "WHERE (" +
                                    fieldForMatching + ' ' +
                                    "LIKE \'%" +
                                    argument + "%\');";
                    if (argument == "")
                        sqlString = "SELECT * FROM " +
                                    table + ';';

                    Console.WriteLine(sqlString);
                    _parentAdapter = new SqlDataAdapter {
                        SelectCommand = new SqlCommand(sqlString, con)
                    };
                    _dataSet = new DataSet();
                    _dataSet.Clear();
                    _parentAdapter.Fill(_dataSet, table);
                    _dataGridViewParent.DataSource = _dataSet.Tables[0];
                }
                catch (Exception e) {
                    MessageBox.Show("Can't connect to database :(" + e, "Error", MessageBoxButtons.OK,
                        MessageBoxIcon.Error);
                }
            }
        }

        private void PopulateChild(string argument) {
            // templated
            const string table = "Exam.dbo.Child";
            const string fieldForeignKey = "ParentID";
            // 
            using (var con = new SqlConnection(ConnectionString)) {
                try {
                    con.Open();
                    var sqlString = "SELECT * FROM " +
                                    table
                                    + ' ' + "WHERE " +
                                    fieldForeignKey
                                    + " = " +
                                    argument + ';';
                    if (argument == "") return;

                    Console.WriteLine(sqlString);
                    _childAdapter = new SqlDataAdapter {
                        SelectCommand = new SqlCommand(sqlString, con)
                    };
                    _dataSet = new DataSet();
                    _dataSet.Clear();
                    _childAdapter.Fill(_dataSet, table);
                    _dataGridViewChild.DataSource = _dataSet.Tables[table];
                }
                catch (Exception e) {
                    MessageBox.Show("Can't connect to database :(" + e, "Error", MessageBoxButtons.OK,
                        MessageBoxIcon.Error);
                }
            }
        }

        private void InitializeGui() {
            // Setup UI
            Size = new Size(1500, 800);
            Text = "Exam Template";
            FormBorderStyle = FormBorderStyle.FixedSingle;

            // _dataGridViewParent
            _dataGridViewParent = new DataGridView {
                Size = new Size(730, 600),
                Location = new Point(10, 4),
                MultiSelect = false,
                SelectionMode = DataGridViewSelectionMode.FullRowSelect,
                ReadOnly = true
            };

            // _dataGridViewChild
            _dataGridViewChild = new DataGridView {
                Size = new Size(730, 600),
                Location = new Point(745, 4),
                MultiSelect = false
            };

            // _searchTextBox
            _searchTextBox = new TextBox {
                Location = new Point(100, 655),
                Size = new Size(500, 25)
            };
            _searchTextBox.Font = new Font(_searchTextBox.Font.FontFamily, 25);

            // _synchronizeButton 
            _synchronizeButton = new Button {
                Location = new Point(1000, 655),
                Size = new Size(200, 40),
                Text = "Synchronize"
            };

            // Controls binding
            Controls.Add(_dataGridViewParent);
            Controls.Add(_dataGridViewChild);
            Controls.Add(_searchTextBox);
            Controls.Add(_synchronizeButton);

            // Event handlers
            _searchTextBox.TextChanged += _searchTextBox_TextChanged;
            _synchronizeButton.Click += _synchronizeButton_Click;
            _dataGridViewParent.SelectionChanged += _dataGridViewParent_SelectionChanged;
        }

        private void _searchTextBox_TextChanged(object sender, EventArgs e) {
            PopulateParent(_searchTextBox.Text);
        }

        private void _synchronizeButton_Click(object sender, EventArgs e) {
            // templated
            const string table = "Exam.dbo.Child";
            //
            try {
                using (var conn = new SqlConnection(ConnectionString)) {
                    _childAdapter.SelectCommand.Connection = conn;
                    var unused = new SqlCommandBuilder(_childAdapter);
                    _childAdapter.Update(_dataSet, table);
                    _dataSet.Tables[table].Clear();
                    _childAdapter.Fill(_dataSet, table);
                }
            }
            catch (Exception ex) {
                MessageBox.Show(ex.Message + '\n' + ex.StackTrace);
            }
        }

        private void _dataGridViewParent_SelectionChanged(object sender, EventArgs e) {
            if (_dataGridViewParent.SelectedCells.Count <= 0) return;

            // templated
            PopulateChild(_dataGridViewParent.Rows[_dataGridViewParent.SelectedCells[0].RowIndex].Cells[0].Value
                .ToString());
            //
        }
    }
}