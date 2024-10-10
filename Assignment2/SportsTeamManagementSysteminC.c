// Somewhy my code doesn't work on the first test on CF,but it works when i run it on my laptop
// Importing libraries
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

// Defining global variables
enum Position{
    Goalkeeper = 0,
    Defender,
    Midfielder,
    Forward
};
int IDs[100];
char Alpahbet[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
char Positions[4][11] = {"Goalkeeper","Defender","Midfielder","Forward"};

struct Player{
    int ID;
    int count;
    int goals;
    int age;
    int position;
    char name[];
};

// Defining functions
void Add(struct Player Players[100],FILE *input, FILE *output){
    // Reading data
    int ID,age,goals;
    char name[256];
    char cmd[100];
    char StringPosition[100];
    fscanf(input,"%s",cmd);
    ID = atoi(cmd);
    fscanf(input,"%s",cmd);
    strcpy(name, cmd);
    fscanf(input,"%s",cmd);
    strcpy(StringPosition,cmd);
    fscanf(input,"%s",cmd);
    age = atoi(cmd);
    fscanf(input,"%s",cmd);
    goals = atoi(cmd);
    // Checking data, we read using flags
    for (int i=0;i<100;i++){
        if (Players[i].count!=1){
            int AgeFlag = 1;
            if ((age >= 18) && (age <= 100)){
                AgeFlag = 0;
                Players[i].age=age;
            }
            if (AgeFlag){
                fprintf(output, "Invalid inputs \n");
                fclose(output);
                exit(1);
            }
            int GoalsFlag = 1;
            if ((goals >= 0)&&(goals < 1000)){
                GoalsFlag = 0;
                Players[i].goals=goals;
            }
            if (GoalsFlag){
                fprintf(output, "Invalid inputs \n");
                fclose(output);
                exit(1);
            }
            int CapitalFlag = 1;
            if (isupper(name[0])){
                CapitalFlag = 0;
                strcpy(Players[i].name,name);
            }
            if (CapitalFlag){
                fprintf(output, "Invalid inputs \n");
                fclose(output);
                exit(1);
            }
            int LenFlag = 1;
            if ((strlen(name) >= 2) && ((strlen(name) <= 15))){
                LenFlag = 0;
                strcpy(Players[i].name,name);
            }
            if (LenFlag){
                fprintf(output, "Invalid inputs \n");
                fclose(output);
                exit(1);
            }
            int LettersFlag = 0;
            for (int i = 0;i < strlen(name);i++){
                if (strchr(Alpahbet, name[i]) == NULL){
                    LettersFlag = 1;
                    fprintf(output, "Invalid inputs \n");
                    fclose(output);
                    exit(1);
                }
            }
            strcpy(Players[i].name, name);
            Players[i].count=1;
            int UniqFlag = 0;
            for (int i = 1;i<100;i++){
                if (ID == IDs[i]){
                    UniqFlag = 1;
                    fprintf(output, "Invalid inputs \n");
                    fclose(output);
                    exit(1);
                }
            }
            if (UniqFlag != 1){
                Players[i].ID=ID;

            }
            int PosFlag = 1;
            for (int m=0;m<4;m++){
                if (strcmp(Positions[m],StringPosition)==0){
                    PosFlag = 0;
                    Players[i].position=m;
                    break;
                }
            }
            if (PosFlag){
                fprintf(output, "Invalid inputs \n");
                fclose(output);
                exit(1);
            }
            break;
        }
    }
}
void Update(struct Player Players[100],FILE *input, FILE *output){
    // Reading Data
    int ID,age,goals;
    char name[100];
    char cmd[100];
    char StringPosition[100];
    fscanf(input,"%s",cmd);
    ID = atoi(cmd);
    fscanf(input,"%s",cmd);
    strcpy(name,cmd);
    fscanf(input,"%s",cmd);
    strcpy(StringPosition,cmd);
    fscanf(input,"%s",cmd);
    age = atoi(cmd);
    fscanf(input,"%s",cmd);
    goals = atoi(cmd);
    // Checking data using flags
    for (int i=0;i<100;i++){
        if (Players[i].ID==ID && Players[i].count ==1) {
            int AgeFlag = 1;
            if ((age >= 18) && (age <= 100)){
                AgeFlag = 0;
                Players[i].age=age;
            }
            if (AgeFlag){
                fprintf(output, "Invalid inputs \n");
                fclose(output);
                exit(1);
            }
            int GoalsFlag = 1;
            if ((goals >= 0)&&(goals < 1000)){
                GoalsFlag = 0;
                Players[i].goals=goals;
            }
            if (GoalsFlag){
                fprintf(output, "Invalid inputs \n");
                fclose(output);
                exit(1);
            }
            int CapitalFlag = 1;
            if (isupper(name[0])){
                CapitalFlag = 0;
                strcpy(Players[i].name,name);
            }
            if (CapitalFlag){
                fprintf(output, "Invalid inputs \n");
                fclose(output);
                exit(1);
            }
            int LenFlag = 1;
            if ((strlen(name) >= 2) && ((strlen(name) <= 15))){
                LenFlag = 0;
                strcpy(Players[i].name,name);
            }
            if (LenFlag){
                fprintf(output, "Invalid inputs \n");
                fclose(output);
                exit(1);
            }
            int LettersFlag = 0;
            for (int i = 0;i < strlen(name);i++){
                if (strchr(Alpahbet, name[i]) == NULL){
                    LettersFlag = 1;
                    fprintf(output, "Invalid inputs \n");
                    fclose(output);
                    exit(1);
                }
            }
            strcpy(Players[i].name, name);
            Players[i].count=1;
            int UniqFlag = 0;
            for (int i = 1;i<100;i++){
                if (ID == IDs[i]){
                    UniqFlag = 1;
                    fprintf(output, "Invalid inputs \n");
                    fclose(output);
                    exit(1);
                }
            }
            if (UniqFlag != 1){
                Players[i].ID=ID;

            }
            int PosFlag = 1;
            for (int m=0;m<4;m++){
                if (strcmp(Positions[m],StringPosition)==0){
                    PosFlag = 0;
                    Players[i].position=m;
                    break;
                }
            }
            if (PosFlag){
                fprintf(output, "Invalid inputs \n");
                fclose(output);
                exit(1);
            }
            break;
        }
    }
}
void Search(struct Player Players[100],FILE *input,FILE *output){
    // Reading data
    char cmd[100];
    fscanf(input,"%s",cmd);
    int ID = atoi(cmd);
    int count =0;
    // Searching player
    for (int i=0;i<100;i++){
        if (Players[i].ID==ID && Players[i].count==1){
            count++;
            fprintf(output,"Found \n");
            break;
        }
    }
    if (count==0){
        fprintf(output,"Not found \n");
    }

}
void  Delete(struct Player Players[100],FILE *input,FILE *output){
    // Reading data
    char cmd[100];
    fscanf(input,"%s",cmd);
    int ID = atoi(cmd);
    int count=0;
    // Deleting player
    for (int i=0;i<100;++i){
        if (Players[i].ID==ID && Players[i].count==1){
            Players[i].count=0;
            count++;
            break;
        }
    }
    if (count==0){
        fprintf(output,"Impossible to delete \n");
    }
}
void Display(struct Player Players[100],FILE *input,FILE *output){
    // Checking if team is not empty
    int count=0;
    for (int i=0;i<100;i++){
        if (Players[i].count==1){
            count++;
            break;
        }
    }
    if (count==0){
        fprintf(output,"Invalid inputs \n");
        fclose(output);
        exit(1);
    }
    // Displaying data players position using enum and string
    enum Position;
    char StringPosition[11];
    for (int i=0;i<100;i++) {
        if (Players[i].count == 1) {
            switch (Players[i].position){
                case Goalkeeper :
                    strcpy(StringPosition,"Goalkeeper");
                    break;
                case Defender :
                    strcpy(StringPosition,"Defender");
                    break;
                case Midfielder :
                    strcpy(StringPosition,"Midfielder");
                case Forward :
                    strcpy(StringPosition,"Forward");
            }
            fprintf(output, "ID: %d, Name: %s, Position: %s, Age: %d, Goals: %d \n", Players[i].ID, Players[i].name,
                    StringPosition, Players[i].age, Players[i].goals);
        }
    }
}

// Running code
int main() {
    // Opening files
    struct Player Players[100];
    FILE *input;
    FILE *output;
    input = fopen("input.txt","r");
    output = fopen("output.txt","w");
    // Check if file opens correctly
    if (input == 0){
        fprintf(input,"Invalid inputs");
        fclose(input);
        fclose(output);
        return 0;
    }
    char cmd[100];
    // Reading data
    while (fgets(cmd,100,input)!=0){
        for (int i=0;i<100;i++){
            if (cmd[i]=='\n'){
                cmd[i]='\0';
                break;
            }
        }
        // Calling functions
        if (strcmp(cmd,"Add")==0){
            Add(Players,input, output);
        }
        if (strcmp(cmd,"Search")==0){
            Search(Players,input,output);
        }
        if (strcmp(cmd,"Update")==0){
            Update(Players,input, output);
        }
        if (strcmp(cmd,"Display")==0){
            Display(Players,input,output);
        }
        if (strcmp(cmd,"Delete")==0){
            Delete(Players,input,output);
        }
    }
    fclose(input);
    fclose(output);
    return 0;
}
