#pragma once


class Wav2 {
public:
	friend class Config; // "static?" CTOR and dtor ofc

	const static int sampleRate = 22050;
	const static int NumSamples = 22050 * 30;   // 30 seconds at MAX

	static HWAVEIN inputHandle;

	static WAVEFORMATEX waveFormat;
	static WAVEHDR waveHeader;

	short static int *audioBuffer;

	struct Config {
		Config() {
			short int *audioBuffer = new short int[NumSamples]; // when this is filled idk what happens :D

			// CONFIG BOILERPLATE
			waveFormat.wFormatTag = WAVE_FORMAT_PCM;      //  simple, uncompressed format
			waveFormat.wBitsPerSample = 16;               //  16 for high quality, 8 for telephone-grade
			waveFormat.nChannels = 2;                     //  1=mono, 2=stereo
			waveFormat.nSamplesPerSec = sampleRate;       //  22050
			waveFormat.nAvgBytesPerSec = waveFormat.nSamplesPerSec * waveFormat.nChannels * waveFormat.wBitsPerSample / 8;
			// = nSamplesPerSec * n.Channels * wBitsPerSample/8
			waveFormat.nBlockAlign = waveFormat.nChannels * waveFormat.wBitsPerSample / 8;
			// = n.Channels * wBitsPerSample/8
			waveFormat.cbSize = 0;
		}

		~Config() {
			delete Wav2::audioBuffer;
		}
	};

public:
	Wav2() {};

	static void startRecording() {
		MMRESULT result = 0;

		result = waveInOpen(&inputHandle, WAVE_MAPPER, &waveFormat, 0L, 0L, WAVE_FORMAT_DIRECT);

		if (result)
		{
			std::cout << "Fail step 1" << std::endl;
			std::cout << result << std::endl;
			Sleep(10000);
			return;
		}

		waveHeader.lpData = (LPSTR)audioBuffer;
		waveHeader.dwBufferLength = NumSamples * 2;
		waveHeader.dwBytesRecorded = 0;
		waveHeader.dwUser = 0L;
		waveHeader.dwFlags = 0L;
		waveHeader.dwLoops = 0L;
		waveInPrepareHeader(inputHandle, &waveHeader, sizeof(WAVEHDR));

		// Insert a wave input buffer
		result = waveInAddBuffer(inputHandle, &waveHeader, sizeof(WAVEHDR));

		if (result)
		{
			std::cout << "Fail step 2" << std::endl;
			std::cout << result << std::endl;
			Sleep(10000);
			return;
		}

		// Start recording
		result = waveInStart(inputHandle);

		if (result)
		{
			std::cout << "Fail step 3" << std::endl;
			std::cout << result << std::endl;
			Sleep(10000);
			return;
		}
	}

	static void stopRecording() {
		waveInClose(inputHandle);
	}


	static void writeToFile(const char* filename) {
		std::ofstream wavFile(filename, std::ios::binary | std::ios::out);
		// @@@ RIFF chunk
		// ChunkID
		wavFile.write("RIFF", 4);
		// Chunk size
		unsigned int subchunk2size = ((waveHeader.dwBytesRecorded / 2) * waveFormat.nChannels * waveFormat.wBitsPerSample) / 8;
		unsigned int x = subchunk2size;
		wavFile.write((char*)&x, sizeof(x));
		// Format
		wavFile.write("WAVE", 4);


		// @@@ Fmt chunk
		wavFile.write("fmt ", 4);
		// fmt size
		x = 16;
		wavFile.write((char*)&x, sizeof(x));
		// audio format
		wavFile.write((char*)&waveFormat.wFormatTag, sizeof(waveFormat.wFormatTag));
		// num channels
		wavFile.write((char*)&waveFormat.nChannels, sizeof(waveFormat.nChannels));
		// sample rate
		wavFile.write((char*)&waveFormat.nSamplesPerSec, sizeof(waveFormat.nSamplesPerSec));
		// byte rate         
		wavFile.write((char*)&waveFormat.nAvgBytesPerSec, sizeof(waveFormat.nAvgBytesPerSec));
		// block align
		wavFile.write((char*)&waveFormat.nBlockAlign, sizeof(waveFormat.nBlockAlign));
		// bits per sample
		wavFile.write((char*)&waveFormat.wBitsPerSample, sizeof(waveFormat.wBitsPerSample));

		// @@@ DATA chunk
		wavFile.write("data", 4);
		// data size
		wavFile.write((char*)&subchunk2size, sizeof(subchunk2size));

		for (u_int i = 0; i < waveHeader.dwBytesRecorded; ++i) {
			wavFile.write((char*)&waveHeader.lpData[i], sizeof(waveHeader.lpData[i]));
		}

		wavFile.close();
	}
};
