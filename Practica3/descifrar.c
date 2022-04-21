#include <stdlib.h>
#include <stdio.h>

void decrypt(unsigned *v, unsigned *k)
{
    unsigned const delta = 0x9e3779b9;
    unsigned sum = 0xC6EF3720;

    for (int i = 0; i < 32; i++)
    {
        v[1] -= (((v[0] << 4) + k[2]) ^ (v[0] + sum) ^ ((v[0] >> 5) + k[3]));
        v[0] -= (((v[1] << 4) + k[0]) ^ (v[1] + sum) ^ ((v[1] >> 5) + k[1]));
        sum -= delta;
    }
}

int main(int argc, char const *argv[])
{
    unsigned k[4] = {128, 129, 130, 131};
    FILE *file, *decryptedFile;

    if (argc < 3)
    {
        perror("Error, el número de argumentos es incorrecto");
        exit(-1);
    }

    if ((file = fopen(argv[1], "rb")) == NULL)
    {
        perror("Error abriendo el fichero a desencriptar");
        exit(-1);
    }

    if ((decryptedFile = fopen(argv[2], "wb")) == NULL)
    {
        perror("Error creando o leyendo el fichero de salida");
        exit(-1);
    }

    printf("Los archivos se han cargado correctamente");

    unsigned fileSize, decryptedFileSize;
    if ((fread(&fileSize, sizeof(unsigned), 1, file)) == 1)
    {
        decryptedFileSize = fileSize;
        if ((fileSize % 8) != 0)
        {
            decryptedFileSize += 8 - (fileSize % 8);
        }

        unsigned *buffer = malloc(decryptedFileSize);
        fread(buffer, decryptedFileSize, 1, file);

        for (int i = 0; i < decryptedFileSize / sizeof(unsigned); i += 2)
        {
            decrypt(&buffer[i], k);
        }

        fwrite(buffer, fileSize, 1, decryptedFile);
        free(buffer);
    }

    fclose(file);
    fclose(decryptedFile);
    return 0;
}
