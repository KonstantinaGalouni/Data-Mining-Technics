n1=1000; %initial space for vectors x1, y1
x1=zeros(1,n1);
y1=zeros(1,n1);

counter=1; % Length of first sequence
fid = fopen('TED_LCSS\coordinates_1.txt');
tl = fgets(fid);
while ischar(tl)
    % Reading coordinates from file
    C_temp = textscan(tl,'%f,%f');
    if counter>n1
        % More space is needed
        x_temp=zeros(1,1000);
        x1=[x1 x_temp];
        y_temp=zeros(1,1000);
        y1=[y1 y_temp];
    
         n1=n1+1000;
    end
    x1(counter)=C_temp{2};
    y1(counter)=C_temp{1};
    tl = fgets(fid);
    counter=counter+1;
end
fclose(fid);

n2=1000; %initial space for vectors x2, y2
x2=zeros(1,n2);
y2=zeros(1,n2);

counter_=1; % Length of second sequence
fid_ = fopen('TED_LCSS\coordinates_2.txt');
tl_ = fgets(fid_);
 while ischar(tl_)
     % Reading coordinates from file
     C_temp_ = textscan(tl_,'%f,%f');
     if counter_>n2
         % More space is needed
         x_temp=zeros(1,1000);
         x2=[x2 x_temp];
         y_temp=zeros(1,1000);
         y2=[y2 y_temp];
         
         n2=n2+1000;
     end
     x2(counter_)=C_temp_{2};
     y2(counter_)=C_temp_{1};
     tl_ = fgets(fid_);
     counter_=counter_+1;
 end
fclose(fid_);
 
plot(x1,y1,x2,y2);