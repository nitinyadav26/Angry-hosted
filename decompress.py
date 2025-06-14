import zipfile
import os

zip_filename = 'AngryBirds-Game-main.zip'  # Replace with your actual ZIP filename

with zipfile.ZipFile(zip_filename, 'r') as zip_ref:
    zip_ref.extractall('extracted_files')

print("Extraction complete. Files are in the 'extracted_files' folder.")
