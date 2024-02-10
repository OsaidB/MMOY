import pymysql
from app import app
from config import mysql
from flask import jsonify
from flask import flash, request

##Osaid Baba
@app.route("/add_course", methods=["POST"])
def create_book():
    try:
        _json = request.json
        _course_code = _json.get("course_code")
        _course_title = _json.get("course_title")
        _instructor = _json.get("instructor")
        _class_num = _json.get("class_num")
        _course_days = _json.get("course_days")
        _time_from = _json.get("time_from")
        _time_to = _json.get("time_to")
        _faculty = _json.get("faculty")
        _room_num = _json.get("room_num")

        if (
            _course_code
            and _course_title
            and _instructor
            and _class_num
            and _course_days
            and _time_from
            and _time_to
            and _faculty
            and _room_num
        ):

            conn = mysql.connect()
            cursor = conn.cursor(pymysql.cursors.DictCursor)

            sqlQuery = """
                        INSERT INTO 
                        courses(course_code, course_title, instructor, class_num,
                                course_days, time_from, time_to, faculty, room_num)
                        VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s)
                        """

            bindData = (
                _course_code,
                _course_title,
                _instructor,
                _class_num,
                _course_days,
                _time_from,
                _time_to,
                _faculty,
                _room_num,
            )

            cursor.execute(sqlQuery, bindData)
            conn.commit()

            respone = jsonify(result="Course added successfully!")
            respone.status_code = 200
            return respone
        else:
            return showMessage()
    except Exception as e:
        print(e)
        return jsonify({"error": str(e)}), 500
    finally:
        if cursor:
            cursor.close()
        if conn:
            conn.close()


@app.route("/add_course_for_student/<int:student_id>", methods=["POST"])
def add_course_for_student(student_id):
    try:
        _json = request.json
        _course_code = _json.get("course_code")
        _course_title = _json.get("course_title")
        _instructor = _json.get("instructor")
        _class_num = _json.get("class_num")
        _course_days = _json.get("course_days")
        _time_from = _json.get("time_from")
        _time_to = _json.get("time_to")
        _faculty = _json.get("faculty")
        _room_num = _json.get("room_num")

        # Validate student_id and course details...

        conn = mysql.connect()
        cursor = conn.cursor(pymysql.cursors.DictCursor)

        # First, insert the course into the 'courses' table if it's not already there
        sql_course_query = """
            INSERT INTO courses(course_code, course_title, instructor, class_num, 
                                course_days, time_from, time_to, faculty, room_num)
            VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s)
            ON DUPLICATE KEY UPDATE course_id = LAST_INSERT_ID(course_id)
        """
        course_data = (
            _course_code,
            _course_title,
            _instructor,
            _class_num,
            _course_days,
            _time_from,
            _time_to,
            _faculty,
            _room_num,
        )
        cursor.execute(sql_course_query, course_data)
        course_id = cursor.lastrowid

        # Then, create a link between the student and the course in 'student_courses' table
        sql_student_course_query = """
            INSERT INTO student_courses(student_id, course_id) 
            VALUES(%s, %s)
        """
        student_course_data = (student_id, course_id)
        cursor.execute(sql_student_course_query, student_course_data)
        conn.commit()

        response = jsonify(result="Course added for student successfully!")
        response.status_code = 200
        return response
    except Exception as e:
        print(e)
        return jsonify({"error": str(e)}), 500
    finally:
        cursor.close()
        conn.close()


@app.route("/login", methods=["POST"])
def login():
    try:
        # Parse the JSON request
        data = request.get_json()
        student_email = data["student_email"]
        passs = data["pass"]

        # Connect to the MySQL database and check credentials
        conn = mysql.connect()
        cursor = conn.cursor(pymysql.cursors.DictCursor)
        cursor.execute(
            "SELECT * FROM students WHERE student_email = %s AND pass = %s",
            (student_email, passs),
        )
        result = cursor.fetchone()

        if result:
            student_id = result["student_id"]  # Get the student_id from the result
            # Return the student_id in the response
            return (
                jsonify(
                    success=True, message="Login successful", student_id=student_id
                ),
                200,
            )

        else:
            return jsonify(success=False, message="Invalid email or password"), 401
    except Exception as e:
        return jsonify(success=False, message=str(e)), 500
    finally:
        cursor.close()
        conn.close()


@app.route("/register", methods=["POST"])
def register():
    try:
        data = request.get_json()

        student_name = data.get("student_name")
        student_email = data.get("student_email")
        password = data.get("pass")
        date_of_birth = data.get("date_of_birth")
        phone_number = data.get("phone_number")
        gender = data.get("gender")

        conn = mysql.connect()
        cursor = conn.cursor(pymysql.cursors.DictCursor)

        # Check if email already exists
        cursor.execute(
            "SELECT student_id FROM students WHERE student_email = %s", (student_email,)
        )
        if cursor.fetchone():
            # If the result is not None, email is already in use
            return jsonify(success=False, message="Email already registered"), 409

        insert_query = """
            INSERT INTO students (student_name, student_email, pass, date_of_birth, phone_number, gender)
            VALUES (%s, %s, %s, %s, %s, %s)
        """

        cursor.execute(
            insert_query,
            (
                student_name,
                student_email,
                password,
                date_of_birth,
                phone_number,
                gender,
            ),
        )
        conn.commit()

        return jsonify(success=True, message="Registration successful"), 201
    except Exception as e:
        conn.rollback()
        return jsonify(success=False, message=str(e)), 500
    finally:
        cursor.close()
        conn.close()


@app.route("/courses")
def courses():
    conn = None
    cursor = None
    try:
        conn = mysql.connect()
        cursor = conn.cursor(pymysql.cursors.DictCursor)
        cursor.execute("SELECT * FROM courses")

        courseRows = cursor.fetchall()
        response = jsonify(courseRows)
        response.status_code = 200
        return response
    except Exception as e:
        print(e)
        return jsonify({"error": str(e)}), 500
    finally:
        if cursor is not None:
            cursor.close()
        if conn is not None:
            conn.close()


@app.route("/student_courses/<int:student_id>")
def student_courses(student_id):
    conn = None
    cursor = None
    try:
        conn = mysql.connect()
        cursor = conn.cursor(pymysql.cursors.DictCursor)
        # This SQL query assumes there is a student_courses table with a student_id foreign key
        cursor.execute(
            """
            SELECT c.* FROM courses c
            JOIN student_courses sc ON c.course_id = sc.course_id
            WHERE sc.student_id = %s
        """,
            (student_id,),
        )

        courseRows = cursor.fetchall()
        response = jsonify(courseRows)
        response.status_code = 200
        return response
    except Exception as e:
        print(e)
        return jsonify({"error": str(e)}), 500
    finally:
        if cursor is not None:
            cursor.close()
        if conn is not None:
            conn.close()
#######################################################################################################
##Mohammad Murrar

########################### Method to get tasks of a specific Student #######################################################
@app.route('/tasks/<int:student_id>')
def task_details(student_id):
    try:
        conn = mysql.connect()
        cursor = conn.cursor(pymysql.cursors.DictCursor)
        cursor.execute("SELECT * FROM tasks WHERE student_id = %s", student_id)
        taskRow = cursor.fetchall()
        respone = jsonify(taskRow)
        respone.status_code = 200
        return respone
    except Exception as e:
        print(e)
    finally:
        cursor.close() 
        conn.close()

########################### Method to get All tasks #######################################################
@app.route("/tasks")
def tasks():
    conn = None
    cursor = None
    try:
        conn = mysql.connect()
        cursor = conn.cursor(pymysql.cursors.DictCursor)
        cursor.execute("SELECT * FROM tasks")

        taskRows = cursor.fetchall()
        response = jsonify(taskRows)
        response.status_code = 200
        return response
    except Exception as e:
        print(e)
        return jsonify({"error": str(e)}), 500
    finally:
        if cursor is not None:
            cursor.close()
        if conn is not None:
            conn.close()
            
###########################Method to get the Course ID from the Course Code###########################
@app.route('/getCourseID/<string:course_code>')
def course_code(course_code):
    try:
        conn = mysql.connect()
        cursor = conn.cursor(pymysql.cursors.DictCursor)
        course_code = "%" + course_code + "%"
        cursor.execute("SELECT course_id FROM courses WHERE course_code LIKE %s", (course_code,))
        courseRows = cursor.fetchall()
        respone = jsonify(courseRows)
        respone.status_code = 200
        return respone
    except Exception as e:
        print(e)
    finally:
        cursor.close() 
        conn.close()
        
###########################Method to Add New Task To The DataBase ####################################
@app.route('/addTask', methods=['POST'])
def create_task():
    try:
        _json = request.json
        _student_id = _json['student_id']
        _course_id = _json['course_id']
        _task_type = _json['task_type']
        _description = _json['description']
        _priority = _json['priority']
        _due_date = _json['due_date']
        _due_time = _json['due_time']

        if _student_id and _course_id and _task_type and _description and _priority and _due_date and _due_time:
            conn = mysql.connect()
            cursor = conn.cursor(pymysql.cursors.DictCursor)
            sqlQuery = "INSERT INTO tasks(student_id, course_id, task_type, description, priority, due_date, due_time) VALUES(%s, %s, %s, %s, %s, %s, %s)"
            bindData = (_student_id, _course_id, _task_type, _description, _priority, _due_date, _due_time)
            cursor.execute(sqlQuery, bindData)
            conn.commit()
            response = jsonify(result='Task added successfully!')
            response.status_code = 200
            return response
        else:
            return showMessage()
    except Exception as e:
        print(e)
    finally:
        cursor.close() 
        conn.close()
#######################################################################################################
##Mahmoud zaatre
# @app.route('/create', methods=['POST'])
# def create_book():
#     try:        
#         _json = request.json
#         _title = _json['title']
#         _category = _json['category']
#         _desc = _json['desc']
#         _price = _json['price']
#         _senderID = _json['senderID']
            
#         if _title and _category and _pages and request.method == 'POST':
#             conn = mysql.connect()
#             cursor = conn.cursor(pymysql.cursors.DictCursor)        
#             sqlQuery = "INSERT INTO books VALUES( %s, NULL, %s, %s, %s, %s)"
#             bindData = (_title, _category, _desc, _price, _senderID)            
#             cursor.execute(sqlQuery, bindData)
#             conn.commit()
#             respone = jsonify(result='Book added successfully!')
#             respone.status_code = 200
#             return respone
#         else:
#             return showMessage()
#     except Exception as e:
#         print(e)
#     finally:
#         cursor.close() 
#         conn.close()          
     
@app.route('/books')
def books():
    try:
        conn = mysql.connect()
        cursor = conn.cursor(pymysql.cursors.DictCursor)
        cursor.execute("SELECT * FROM book")
        bookRows = cursor.fetchall()
        respone = jsonify(bookRows)
        respone.status_code = 200
        return respone
    except Exception as e:
        print(e)
    finally:
        cursor.close() 
        conn.close()  

@app.route('/books/<int:book_id>')
def book_details(book_id):
    try:
        conn = mysql.connect()
        cursor = conn.cursor(pymysql.cursors.DictCursor)
        cursor.execute("SELECT * FROM book WHERE senderID = %s", book_id)
        bookRow = cursor.fetchone()
        respone = jsonify(bookRow)
        respone.status_code = 200
        return respone
    except Exception as e:
        print(e)
    finally:
        cursor.close() 
        conn.close()

@app.route('/booksB/<int:book_id>')
def book_Res(book_id):
    try:
        conn = mysql.connect()
        cursor = conn.cursor(pymysql.cursors.DictCursor)
        cursor.execute("SELECT * FROM book WHERE buyerId = %s", book_id)
        bookRow = cursor.fetchone()
        respone = jsonify(bookRow)
        respone.status_code = 200
        return respone
    except Exception as e:
        print(e)
    finally:
        cursor.close() 
        conn.close()        

@app.route('/getbook/<string:cat>')
def book_cat(cat):
    try:
        conn = mysql.connect()
        cursor = conn.cursor(pymysql.cursors.DictCursor)
        cursor.execute("SELECT * FROM books WHERE category LIKE %s", cat)
        bookRows = cursor.fetchall()
        respone = jsonify(bookRows)
        respone.status_code = 200
        return respone
    except Exception as e:
        print(e)
    finally:
        cursor.close() 
        conn.close() 
        
@app.route('/getID/<string:title>')
def book_id(title):
    try:
        conn = mysql.connect()
        cursor = conn.cursor(pymysql.cursors.DictCursor)
        cursor.execute("SELECT senderID FROM book WHERE title LIKE %s", title)
        bookRows = cursor.fetchall()
        respone = jsonify(bookRows)
        respone.status_code = 200
        return respone
    except Exception as e:
        print(e)
    finally:
        cursor.close() 
        conn.close()


@app.route('/update', methods=['PUT'])
def update_book():
    try:
        _json = request.json
        _id = _json['id']
        _title = _json['title']
        _category = _json['category']
        _pages = _json['pages']
        if _title and _category and _pages and request.method == 'PUT':            
            sqlQuery = "UPDATE books SET title=%s, category=%s, pages=%s WHERE id=%s"
            bindData = (_title, _category, _pages, _id,)
            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.execute(sqlQuery, bindData)
            conn.commit()
            respone = jsonify('Book updated successfully!')
            respone.status_code = 200
            return respone
        else:
            return showMessage()
    except Exception as e:
        print(e)
    finally:
        cursor.close() 
        conn.close() 

@app.route('/delete/<int:id>', methods=['DELETE'])
def delete_book(id):
    try:
        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute("DELETE FROM BOOKS WHERE id =%s", (id))
        conn.commit()
        respone = jsonify('Book deleted successfully!')
        respone.status_code = 200
        return respone
    except Exception as e:
        print(e)
    finally:
        cursor.close() 
        conn.close()

#######################################################################################################
##Yazan Hmoum
        
@app.errorhandler(404)
def showMessage(error=None):
    message = {
        "status": 404,
        "message": "Record not found: " + request.url,
    }
    respone = jsonify(message)
    respone.status_code = 404
    return respone


if __name__ == "__main__":
    app.debug = True
    app.run()
