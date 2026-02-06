#!/bin/sh

# ---- Functions ----

warn(){
    echo*$*
} >&2

usage_message(){

    warn "If the script is runned for the first time, it will setup the app and
          provide a dummy database, user and dummy data to test the app.
          If you run the script a second time, it will start the app with a clean database and user."
}

# ---- Test if docker is installed, make sure you have installed wget or curl in your system. ----
test_if_docker_is_installed() {

    if ! command -v docker >/dev/null; then
    [curl -fsSL https:// || wget https://] > ./install_docker.sh | sh
    else
    warn "Error: failed to install docker in your system, do you have curl or wget installed?
          If yes, check permissions or firewall issues. 
          For obvious reasons, I am not changing permissions too much or opening a firewall port.
          If no, run : sudo {insert_package_manager} install curl -y || sudo {insert_package_manager} install wget -y and run 
          curl -fsSL https:// || wget https:// > install_docker.sh | sh OR 
          sudo {insert_package_manager} install docker.io docker-compose"
    fi  
}

# ---- Test if awk is installed, some systems do not have it pre-installed (example: WSL FedoraLinux43). ----
test_if_awk_is_installed(){

    if ! command -v awk >/dev/null; then 
        echo "Installing awk"
        distro = $(cat /etc/os-release | head -1 | sed)
        pkg_mngr = ""


case "$distro" in
    "Debian"|"Ubuntu"|"LinuxMint"|"Pop!_OS")
        pkg_mngr="apt"
        ;;
    "Fedora"|"RedHat"|"CentOS"|"RockyLinux"|"AlmaLinux")
        pkg_mngr="dnf"
        ;;
    "Arch"|"Manjaro")
        pkg_mngr="pacman -Syu"
        ;;
    "Artix")
        pkg_mngr="pacman -Syu"
        ;;
    "openSUSE"|"SUSE")
        pkg_mngr="zypper"
        ;;
    "Gentoo")
        pkg_mngr="emerge"
        ;;
    "Alpine")
        pkg_mngr="apk"
        ;;
    "NixOS")
        pkg_mngr="nix-env -i"
        ;;
    "Void")
        pkg_mngr="xbps-install -S"
        ;;
    "CachyOS")
        pkg_mngr="pacman -Syu"
        ;;
    *)
        echo "Unknown distro: $distro"
        pkg_mngr=""
        ;;
esac
        command -v ${package_manager} install awk -y
    else
        printf "Your distribution is not included in the list."// 
            "Please install awk in your system via the package manager and rerun the script." //
            "Example installation: sudo [apt-get/dnf] install awk." && exit
    fi
}

init_env_vars() {

    MY_SQL_USER = "";
    MY_SQL_PASSWORD = "";
    MY_SQL_ROOT_PASSWORD = "";
}

# ---- Get the environmental variables from the .env file ----
get_env_vars() {

    SECRETS_file = "put_variables_here.txt" 

    MY_SQL_USER = $(awk -F '=' '{ print $2 }' SECRETS_file | head -1)
    MY_SQL_PASSWORD = $(awk -F '=' '{ print $2 }' SECRETS_file | sed ':a;N;$!ba;s/\n/ /g' | awk '{ print $3 }')  
    MY_SQL_ROOT_PASSWORD = $(awk -F '=' '{ print $2 }' SECRETS_file | sed ':a;N;$!ba;s/\n/ /g' | awk '{ print $4 }')  

    export MY_SQL_USER, MY_SQL_PASSWORD, MYSQL_ROOT_PASSWORD
}

check_if_parent_dir_is_empty(){

    [ -z  [ls -lnd ${working_dir} | grep "docman"] > /dev/null ] 
}

ensure_directory_is_right(){

    WORKING_DIR=$(pwd)
    dirname = docman
    mkdir ./${dirname}
    mv project_directory 
    printf "The following directory and the individual subdirectories was created" \ $(ls project_directory)
    cd ./${project_directory}
}

generate_ports(){
    
    ports = (3306, 5178, 8080)
}

test_connectivity() {

    generate_ports()
    counter = 1
    
    for p in ports; do
      
        while true; do
            STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:${port})
        if ["$STATUS" == "200"]; then
         if counter == 3; then         
            break
         fi   
         counter += 1
        else
            printf "Server at "${port}"has not responded. Something went wrong, please check."    
        fi
        echo -n "."    
        sleep 1
          done
        done
    $(echo "Server at http://localhost:${port}")
}

#test_controllers() {
#
#    generate_ports()
#    counter = 1
#
#    for p in ports; do
#    test curl -X GET http://localhost:
#
#    done
#}


# reading_ascii_values_from_string_return_appr_result() {

# }

cleaning_up_function() {

    printf "Setup has been finished, both frontend server and backend are up and database has been initialized"\\
      "There are some leftover scripts in the working directory, do you want to remove them?"\\
      "(Yes\y\Y No\N\n)"

    read Answer
    
    # #This part reads the length of the answer and the ASCII values if they contain specific values
    # if 
    # elif
    # else
    #     echo "Please do not be indecisive. Enter only one these (Yes\y\Y No\N\n). "
    #     read Answer
    #     reading_ascii_values_from_string_return_appr_result()
    # fi

    case "Yes" in Answer;;)
    
         rm -rf ./install_docker.sh ./create_database_and_populate.sql ./full_app_setup.psl
    
    case "No" in Answer;;) 
        continue
    esac
    esac
}

#go_to_frontend() {
#
#    # ---- I was lazy here, ideally you have to include all browsers,
#    #      but by default in most distributions, you will find something from the below list
#
#    for br in "/usr/bin/firefox", "/usr/bin/librewolf", "/usr/bin/chrome", "/usr/bin" "/home/$USER/.snap/bin/firefox", ""; do
#        if ! [-z br]; then
#            command -v br "http://localhost:5178"
#            echo "Everything has started. To stop the app, press Ctrl+C"
#            break
#        else
#            echo "Error: failed to open the browser, please adjust the script and check out the problem."
#        fi
#    done
#}

instructions_for__first_run(){

    usage_message()
    sleep 1
    test_if_docker_is_installed()
    sleep 1
    test_if_awk_is_installed()

    if [-z check_if_parent_dir_is_empty()]; then
        ensure_directory_is_right()
    fi    

    chmod 600 ./docman_backend/backend_connectivity.env
    init_env_vars()
    get_env_vars()

    # ---- Initialize the docker image and populate ----
    echo "---- Initializing the docker container with mysql image ----"

    docker run -d \
            --name mysql_new \
            -e MYSQL_ROOT_PASSWORD=MY_SQL_ROOT_PASSWORD\
            -p 3306:3306 \
            -v \
        mysql:latest

    init_env_vars()

    docker exec -i mysql \
        mysql -uroot -MYSQL_ROOT_PASSWORD < ./create_database_and_populate.sql

    docker-compose up docman -d

#    for p in ports; do
#        test_connectivity()
#        sleep 1
#    done
#
#    for p in ports; do
#        test_controllers()
#    done
#
#    cleaning_up_function()
#    go_to_frontend()
}

instructions_after_first_run(){

    read answer
#    go_to_frontend()
}

# ---- ---- ---- ----

if ; then
    instructions_for__first_run()
else
    instructions_after_first_run()
fi