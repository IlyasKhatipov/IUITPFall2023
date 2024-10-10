# include <stdio.h>
# include <string.h>

int min(int a, int b) {
    if (a > b) {
        return a;
    }
    return b;
}

int check(int a, int b) {
    int flag = 1;
    if ((a < 2 || a > 50) || (b < 1 || b > min(a - 1, 26))) {
        flag = 0;
    }
    return flag;
}

int main() {
    char symbols[] = "ABCDEFGHIJKLMOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789()*!@#$%&^";
    char alphabet[] = "abcdefghijklmnopqrstuvwxyz";
    FILE *input = fopen("input.txt", "r");
    FILE *output = fopen("output.txt", "w");
    int n;
    fscanf(input, "%d", &n);
    char s[n];
    fscanf(input, "%s", &s);
    int m;
    fscanf(input, "%d", &m);
    int a[m];
    for (int i = 0; i < m; i++) {
        fscanf(input, "%d", &a[i]);
    }
    fclose(input);
    if (check(n, m) == 0) {
        fprintf(output, "%s", "Invalid inputs\n");
        fclose(output);
        return 0;
    }
    for (int i = 0; i < m; i++) {
        if (a[i] >= n || a[i] < 1 || a[i] > 26) {
            fprintf(output, "%s", "Invalid inputs\n");
            fclose(output);
            return 0;
        }
    }
    for (int i = 0; i <= n; i++) {
        if (strchr(symbols, s[i]) == NULL) {
            fprintf(output, "%s", "Invalid inputs\n");
            fclose(output);
            return 0;
        }
    }
    for (int i = 0; i < m; i++) {
        s[a[i]] = alphabet[a[i] - 1];
    }
    fprintf(output, "%s\n", s);
    fclose(output);
    return 0;
}
