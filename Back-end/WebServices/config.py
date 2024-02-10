from app import app
from flaskext.mysql import MySQL

mysql = MySQL()
app.config['MYSQL_DATABASE_USER'] = 'root'
# app.config['MYSQL_DATABASE_PASSWORD'] = ''
app.config['MYSQL_DATABASE_PASSWORD'] = '1234'
app.config['MYSQL_DATABASE_DB'] = 'final_android'
app.config['MYSQL_DATABASE_HOST'] = 'localhost'

app.config['MYSQL_DATABASE_PORT'] = 3308

mysql.init_app(app)